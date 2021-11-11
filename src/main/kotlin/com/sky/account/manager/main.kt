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

import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.ui.AppNav
import com.sky.account.manager.ui.common.BearWindow
import com.sky.account.manager.ui.home.HomeUI
import com.sky.account.manager.ui.login.LoginUI
import com.sky.account.manager.ui.register.RegisterUI
import com.sky.account.manager.ui.splash.SplashUI
import com.sky.account.manager.ui.view.MessageUI

/**
 * Created by sky on 2021/8/26.
 */
fun main() = application {

    val context = rememberAppContext()
    val appState = rememberAppState(context)

    BearWindow(
        onCloseRequest = {
            context.release()
            exitApplication()
        },
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(1000.dp, 800.dp)
        )
    ) {
        Surface {
            when(appState.navType()) {
                AppNav.SPLASH -> {
                    SplashUI()
                }
                AppNav.REGISTER -> {
                    RegisterUI(appState)
                }
                AppNav.LOGIN -> {
                    LoginUI(appState)
                }
                AppNav.HOME -> {
                    HomeUI(appState)
                }
            }
            MessageUI()
        }
    }
}
