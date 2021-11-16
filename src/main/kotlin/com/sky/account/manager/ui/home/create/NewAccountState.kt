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

package com.sky.account.manager.ui.home.create

import androidx.compose.runtime.*
import com.sky.account.manager.AppState
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ui.base.AbstractState
import com.sky.account.manager.util.CheckUtil
import kotlinx.coroutines.launch

/**
 * Created by sky on 2021-11-16.
 */
@Composable
fun rememberNewAccountState(
    appState: AppState
) = remember {
    NewAccountState(appState)
}

class NewAccountState(
    appState: AppState
) : AbstractState(appState) {

    private val admin: AdminItem
        get() = appState.admin

    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var url by mutableStateOf("")
    var desc by mutableStateOf("")

    /**
     * 创建账号
     */
    fun create(
        name: String,
        password: String,
        url: String,
        desc: String
    ) {
        if (!CheckUtil.checkUser(name, password, this::showMessage)) {
            return
        }

        scope.launch {
            repository.create(
                appState.encrypt(
                    AccountItem.valueOf(
                        adminId = admin.id,
                        name = name,
                        password = password,
                        url = url,
                        desc = desc
                    )
                )
            ).doSuccess {
                reset()
                showMessage("创建账号成功！")
            }.doFailure {
                showMessage("${it.message}")
            }
        }
    }

    /**
     * 重置
     */
    private fun reset() {
        name = ""
        password = ""
        url = ""
        desc = ""
    }
}