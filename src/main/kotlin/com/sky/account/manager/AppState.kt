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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ex.getAppRepository
import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IAppRepository
import com.sky.account.manager.ui.AppNav
import com.sky.account.manager.util.CheckUtil
import com.sky.account.manager.util.MD5Util
import com.sky.account.manager.util.SecretUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun rememberAppState(context: IAppContext) = remember {
    AppState(context)
}

class AppState(
    private val context: IAppContext
) {

    private val repository: IAppRepository by lazy { context.getAppRepository() }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val _loading = mutableStateOf(false)
    private val _chooseFile = mutableStateOf(false)
    private val _message = mutableStateOf("")

    private var _appNav = mutableStateOf(AppNav.HOME)

    private val _admin = mutableStateOf(AdminItem.EMPTY)
    private val _accounts = mutableStateOf(ArrayList<AccountItem>())

    var appNav: AppNav
        get() = _appNav.value
        private set(value) { _appNav.value = value }

    var message: String
        get() = _message.value
        private set(value) { _message.value = value }

    var admin: AdminItem
        get() = _admin.value
        private set(value) { _admin.value = value }

    var accounts: ArrayList<AccountItem>
        get() = _accounts.value
        private set(value) { _accounts.value = value }

    var chooseFile: Boolean
        get() = _chooseFile.value
        set(value) { _chooseFile.value = value }

    /**
     * 清除消息
     */
    fun resetMessage() {
        message = ""
    }

    init {

        initData()
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
                resetMessage()
                admin = it.copy(password = password)
                appNav = AppNav.HOME
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
                resetMessage()
                admin = it.copy(password = password)
                appNav = AppNav.HOME
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
                // 创建成功

            }.doFailure {
                message = "${it.message}"
            }
        }
    }

    private fun initData() {

//        scope.launch {
//
//            val isRegister = repository.isRegister()
//
//            delay(800)
//
//            appNav = if (isRegister) {
//                AppNav.REGISTER
//            } else {
//                AppNav.LOGIN
//            }
//        }

        _accounts.value = arrayListOf(
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