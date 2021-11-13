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

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.AppState
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.common.BearBigTitle
import com.sky.account.manager.ui.common.BearEditText
import com.sky.account.manager.ui.common.BearWindow
import com.sky.account.manager.ui.common.BigBearButton

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun FrameWindowScope.LoginUI(
    appState: AppState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(10.dp))
            BearBigTitle(stringResource("label.welcome"))
            Spacer(Modifier.height(40.dp))

            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            BearEditText(
                icon = painterResource("image/ic_user.svg"),
                label = stringResource("label.userName"),
                value = name
            ) {
                name = it
            }

            Spacer(Modifier.height(20.dp))

            BearEditText(
                icon = painterResource("image/ic_password.svg"),
                label = stringResource("label.password"),
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password
            ) {
                password = it
            }

            Spacer(Modifier.height(40.dp))

            BigBearButton(stringResource("label.login")) {
                // 登录
                appState.login(name, password)
            }
        }
    }
}