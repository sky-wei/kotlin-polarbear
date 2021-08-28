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

import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.application
import com.sky.account.manager.ui.theme.PolarBearTheme
import com.sky.account.manager.util.ALog

/**
 * Created by sky on 2021/8/26.
 */
@ExperimentalComposeUiApi
fun main() = application {

    rememberAppContext()

    ALog.d(">>>>>>>>>>> aaaa")
    ALog.e(">>>>>>>>>>> bbbb")

    AppManager.setEvents(
        onAppStart = {
            println(">>>>  启动")
        },
        onAppExit = {
            println(">>>>>>> 退出")
        }
    ).apply {
        println(">>>>> yyyy")
    }

    Window {

        PolarBearTheme {

            var text by remember {
                mutableStateOf("Hello, World!")
            }

            test(text) {
                text = "Hello, Desktop! ${System.currentTimeMillis()}"
            }
        }
    }
}

@Composable
fun test(name: String, onClick: () -> Unit) {

    Button(onClick = onClick) {
        Text(name)
    }
}
