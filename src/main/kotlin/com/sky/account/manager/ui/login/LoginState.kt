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

package com.sky.account.manager.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.sky.account.manager.AppState
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ui.base.AbstractState
import com.sky.account.manager.util.CheckUtil
import com.sky.account.manager.util.MD5Util
import kotlinx.coroutines.launch

/**
 * Created by sky on 2021-11-16.
 */
@Composable
fun rememberLoginState(
    appState: AppState
) = remember {
    LoginState(appState)
}

class LoginState(
    appState: AppState
) : AbstractState(appState) {

    /**
     * 登录
     */
    fun login(
        name: String,
        password: String
    ) {
        if (!CheckUtil.checkUser(name, password, this::showMessage)) {
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
                appState.toHome(it.copy(password = password))
            }.doFailure {
                showMessage("${it.message}")
            }
        }
    }
}