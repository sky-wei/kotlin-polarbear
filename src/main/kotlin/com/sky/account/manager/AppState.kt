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
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ex.getAppRepository
import com.sky.account.manager.interfaces.IAppRepository
import com.sky.account.manager.ui.AccountNav
import com.sky.account.manager.ui.AppNav
import com.sky.account.manager.util.CheckUtil
import com.sky.account.manager.util.MD5Util
import com.sky.account.manager.util.SecretUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    var chooseFile: Boolean by mutableStateOf(false)
    var message: String? by mutableStateOf(null)
        private set

    var appNav: AppNav by mutableStateOf(AppNav.SPLASH)
        private set
    var admin: AdminItem by mutableStateOf(AdminItem.EMPTY)
        private set

    val newAccountState by lazy { NewAccountState(this) }
    val accountListState by lazy { AccountListState(this) }

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
            val result = repository.register(
                AdminItem.valueOf(
                    name = name,
                    password = MD5Util.md5sum(password)
                )
            )

            result.doSuccess {
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
            val result = repository.login(
                AdminItem.valueOf(
                    name = name,
                    password = MD5Util.md5sum(password)
                )
            )

            result.doSuccess {
                // 登录成功
                toHome(it.copy(password = password))
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
            val result = repository.create(
                AccountItem.valueOf(
                    adminId = admin.id,
                    name = name,
                    password = SecretUtil.encrypt(admin.password, password),
                    url = url,
                    desc = desc
                )
            )

            result.doSuccess {
                newAccountState.reset()
                message = "创建账号成功！"
            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    /**
     * 修改账号
     */
    fun change(account: AccountItem) {

    }

    /**
     * 切换到Home
     */
    private fun toHome(admin: AdminItem) {
        cleanMessage()
        this.admin = admin
        this.appNav = AppNav.HOME
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

        accountListState.accounts = arrayListOf(
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
            AccountItem.valueOf(0, "AAA", "AAA", "http://www.baidu.com", "哈哈，哈哈，哈哈"),
        )
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

    var accountNav: AccountNav by mutableStateOf(AccountNav.LIST)
    var accounts: ArrayList<AccountItem> by mutableStateOf(ArrayList())
    var account: AccountItem? by mutableStateOf(null)
}