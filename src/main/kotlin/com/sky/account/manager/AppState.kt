/*
 * Copyright (c) 2021 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.account.manager

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sky.account.manager.data.DataException
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ex.getAppRepository
import com.sky.account.manager.interfaces.IAppRepository
import com.sky.account.manager.ui.AccountNav
import com.sky.account.manager.ui.AppNav
import com.sky.account.manager.ui.ProfileNav
import com.sky.account.manager.util.Alog
import com.sky.account.manager.util.CheckUtil
import com.sky.account.manager.util.MD5Util
import com.sky.account.manager.util.SecretUtil
import kotlinx.coroutines.*
import java.io.File

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun rememberAppState(onAppExit: () -> Unit) = remember {
    AppState(AppContext(), onAppExit)
}

class AppState(
    private val context: AppContext,
    private val onAppExit: () -> Unit
) {

    private val repository: IAppRepository by lazy { context.getAppRepository() }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    val windowState = WindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1000.dp, 800.dp)
    )

    var loading: Boolean by mutableStateOf(false)
        private set
    var message: String? by mutableStateOf(null)
        private set

    var appNav: AppNav by mutableStateOf(AppNav.SPLASH)
        private set
    var admin: AdminItem by mutableStateOf(AdminItem.EMPTY)
        private set

    val profileState = ProfileState(this)
    val newAccountState = NewAccountState(this)
    val accountListState = AccountListState(this)
    val settingsState = SettingsState(this)

    /**
     * 清除消息
     */
    fun cleanMessage() {
        message = null
    }

    init {
        initData()
    }

    /**
     * 退出
     */
    fun exit() {
        context.release()
        onAppExit()
    }

    /**
     * 注册账号
     */
    fun register(
        name: String,
        password: String,
        confirmPassword: String
    ) {
        if (!CheckUtil.checkRegister(
                name, password, confirmPassword
            ) { message = it }
        ) {
            return
        }

        scope.launch {
            // 注册
            repository.register(
                AdminItem.valueOf(
                    name = name,
                    password = MD5Util.md5sum(password)
                )
            ).doSuccess {
                // 注册成功
                toHome(it.copy(password = password))
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 登录
     */
    fun login(
        name: String,
        password: String
    ) {
        if (!CheckUtil.checkUser(name, password) { message = it }) {
            return
        }

        scope.launch {
            // 登录
            repository.login(
                AdminItem.valueOf(
                    name = name,
                    password = MD5Util.md5sum(password)
                )
            ).doSuccess {
                // 登录成功
                toHome(it.copy(password = password))
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 修改
     */
    fun change(
        item: AdminItem,
        oldPassword: String,
        newPassword: String,
        desc: String
    ) {
        if (!CheckUtil.checkUser(item, oldPassword, newPassword) { message = it }) {
            return
        }

        scope.launch {
            repository.load(item.id)
                .doSuccess {
                    repository.update(
                        item.copy(
                            password = MD5Util.md5sum(newPassword),
                            desc = desc
                        ),
                        it.map { account ->
                            // 解密
                            decrypt(account, oldPassword)
                        }.map { account ->
                            // 加密
                            encrypt(account, newPassword)
                        }
                    ).doSuccess {
                        admin = item.copy(
                            password = newPassword,
                            desc = desc
                        )
                        profileState.backDisplay()
                        accountListState.refresh = true
                    }.doFailure {
                        message = "${it.message}"
                    }
                }.doFailure {
                    message = "${it.message}"
                }
        }
    }

    /**
     * 加载账号列表
     */
    fun refreshList() {

        scope.launch {
            // 加载账号
            repository.load(admin.id)
                .doSuccess {
                    accountListState.refresh = false
                    accountListState.accounts = it
                }.doFailure {
                    message = "${it.message}"
                }
        }
    }

    /**
     * 创建账号
     */
    fun create(
        name: String,
        password: String,
        url: String,
        desc: String
    ) {

        if (!CheckUtil.checkUser(name, password) { message = it }) {
            return
        }

        scope.launch {
            repository.create(
                encrypt(
                    AccountItem.valueOf(
                        adminId = admin.id,
                        name = name,
                        password = password,
                        url = url,
                        desc = desc
                    )
                )
            ).doSuccess {
                newAccountState.reset()
                accountListState.refresh = true
                message = "创建账号成功！"
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 修改账号
     */
    fun change(item: AccountItem) {

        if (!CheckUtil.checkUser(item) { message = it }) {
            return
        }

        scope.launch {
            repository.update(
                encrypt(item)
            ).doSuccess {
                accountListState.backList()
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 删除账号
     */
    fun delete(item: AccountItem) {
        scope.launch {
            repository.delete(
                encrypt(item)
            ).doSuccess {
                accountListState.backList()
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 搜索
     */
    fun search(keyword: String) {
        scope.launch {
            if (keyword.isEmpty()) {
                repository.load(admin.id)
            } else {
                repository.search(admin.id, keyword)
            }.doSuccess {
                accountListState.accounts = it
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 导入
     */
    fun import() {
        scope.launch {
            settingsState
                .importDialog
                .awaitResult()
                ?.run { import(this) }
        }
    }

    /**
     * 导出
     */
    fun export() {
        scope.launch {
            settingsState
                .exportDialog
                .awaitResult()
                ?.run { export(this) }
        }
    }

    /**
     * 加密
     */
    fun encrypt(
        item: AccountItem,
        password: String = admin.password
    ): AccountItem {
        return item.copy(
            password = SecretUtil.encrypt(password, item.password)
        )
    }

    /**
     * 解密
     */
    fun decrypt(
        item: AccountItem,
        password: String = admin.password
    ): AccountItem {
        return item.copy(
            password = SecretUtil.decrypt(password, item.password)
        )
    }

    /**
     * 切换到Home
     */
    private fun toHome(admin: AdminItem) {
        cleanMessage()
        this.admin = admin
        this.appNav = AppNav.HOME
    }

    /**
     * 导入
     */
    private suspend fun import(file: File) {
        try {
            var count = 0
            repository.create(
                Gson().fromJson<List<AccountItem>>(
                    file.readText()
                ).sortedBy {
                    it.id
                }.map {
                    encrypt(it).copy(
                        id = 0,
                        adminId = admin.id,
                        createTime = System.currentTimeMillis() + (count++)
                    )
                }
            ).doSuccess {
                accountListState.refresh = true
                message = "导入成功！"
            }.doFailure {
                message = "${it.message}"
            }
        } catch (tr: Throwable) {
            message = tr.message
            Alog.e("处理异常", tr)
        }
    }

    /**
     * 导出
     */
    private suspend fun export(file: File) {
        try {
            repository.load(admin.id)
                .doSuccess {
                    file.writeText(
                        GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(
                                it.map { item -> decrypt(item) }
                            )
                    )
                    message = "导出成功！"
                }
                .doFailure {
                    message = "${it.message}"
                }
        } catch (tr: Throwable) {
            message = tr.message
            Alog.e("处理异常", tr)
        }
    }

    private fun initData() {

        scope.launch {

            val isRegister = repository.isRegister()

            delay(800)

            appNav = if (isRegister) {
                AppNav.REGISTER
            } else {
                AppNav.LOGIN
            }
        }
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
}

class ProfileState(
    private val appState: AppState
) {
    var profileNav by mutableStateOf(ProfileNav.DISPLAY)

    fun backDisplay() {
        profileNav = ProfileNav.DISPLAY
    }
}

class NewAccountState(
    private val appState: AppState
) {
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var url by mutableStateOf("")
    var desc by mutableStateOf("")

    fun reset() {
        name = ""
        password = ""
        url = ""
        desc = ""
    }
}

class AccountListState(
    private val appState: AppState
) {
    private var _account: AccountItem? by mutableStateOf(null)

    var search: String by mutableStateOf("")
    var accountNav: AccountNav by mutableStateOf(AccountNav.LIST)
    var refresh: Boolean by mutableStateOf(true)
    var accounts: List<AccountItem> by mutableStateOf(ArrayList())
    var account: AccountItem
        get() = _account?: throw DataException("数据不能为空!")
        set(value) { _account = appState.decrypt(value) }

    fun backList() {
        _account = null
        accountNav = AccountNav.LIST
        refresh = true
    }
}

class SettingsState(
    private val appState: AppState
) {

    val importDialog = DialogState<File?>()
    val exportDialog = DialogState<File?>()
}

class DialogState<T> {

    private var onResult: CompletableDeferred<T>? by mutableStateOf(null)

    val isAwaiting get() = onResult != null

    suspend fun awaitResult(): T {
        onResult = CompletableDeferred()
        val result = onResult!!.await()
        onResult = null
        return result
    }

    fun onResult(result: T) = onResult!!.complete(result)
}