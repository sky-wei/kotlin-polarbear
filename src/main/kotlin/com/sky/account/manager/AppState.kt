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
import com.sky.account.manager.ui.NavType
import com.sky.account.manager.ui.view.cleanMessage
import com.sky.account.manager.ui.view.showMessage
import com.sky.account.manager.util.MD5Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun rememberAppState() = remember {
    AppState()
}

class AppState {

    private val context: IAppContext = AppContext()
    private val repository: IAppRepository by lazy { context.getAppRepository() }

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val navType = mutableStateOf(NavType.HOME)
    private val adminUser = mutableStateOf(AdminItem.valueOf("", ""))
    private val accounts = mutableStateOf(ArrayList<AccountItem>())

    fun navType(): NavType {
        return navType.value
    }

    fun adminUser(): AdminItem {
        return adminUser.value
    }

    fun accounts(): List<AccountItem> {
        return accounts.value
    }

    init {

        initData()
    }

    /**
     * 注册账号
     */
    fun register(
        userName: String,
        password: String,
        confirmPassword: String
    ) {

        if (userName.isEmpty()) {
            showMessage("用户名不能为空！")
            return
        }

        if (password.isEmpty()) {
            showMessage("密码不能为空！")
            return
        }

        if (password != confirmPassword) {
            showMessage("输入的密码不一致！")
            return
        }

        scope.launch {
            // 注册
            val result = repository.register(
                AdminItem.valueOf(
                    name = userName,
                    password = MD5Util.md5sum(password)
                )
            )

            result.doSuccess {
                // 注册成功
                cleanMessage()
                adminUser.value = it.copy(password = password)
                navType.value = NavType.HOME
            }.doFailure {
                showMessage("${it.message}")
            }
        }
    }

    /**
     * 登录
     */
    fun login(
        userName: String,
        password: String
    ) {

        if (userName.isEmpty()) {
            showMessage("用户名不能为空！")
            return
        }

        if (password.isEmpty()) {
            showMessage("密码不能为空！")
            return
        }

        scope.launch {
            // 登录
            val result = repository.login(
                AdminItem.valueOf(
                    name = userName,
                    password = MD5Util.md5sum(password)
                )
            )

            result.doSuccess {
                // 登录成功
                cleanMessage()
                adminUser.value = it.copy(password = password)
                navType.value = NavType.HOME
            }.doFailure {
                showMessage("${it.message}")
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
//            if (isRegister) {
//                navType.value = NavType.REGISTER
//            } else {
//                navType.value = NavType.LOGIN
//            }
//        }

        accounts.value = arrayListOf(
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