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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.AppState
import com.sky.account.manager.ui.NavType
import com.sky.account.manager.ui.window.PolarBearWindow

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun AppLogin(
    appState: AppState,
    onCloseRequest: () -> Unit,
) {
    PolarBearWindow(
        onCloseRequest = onCloseRequest,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = WindowSize(400.dp, 300.dp)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(10.dp))

                LoginTitle("Welcome")

                Spacer(Modifier.height(20.dp))

                var text by remember {
                    mutableStateOf("Hello, World!")
                }

                test(text) {
                    text = "Hello, Desktop! ${System.currentTimeMillis()}"
                    appState.navType = NavType.HOME
                }
            }
        }
    }
}

@Composable
fun LoginTitle(
    text: String
) {
    Text(
        text = text,
        fontSize = 36.sp
    )
}



@Composable
fun test(name: String, onClick: () -> Unit) {

    Button(onClick = onClick) {
        Text(name)
    }
}