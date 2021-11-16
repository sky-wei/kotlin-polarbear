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
import androidx.compose.ui.window.application
import com.sky.account.manager.ui.AppNav
import com.sky.account.manager.ui.common.BearWindow
import com.sky.account.manager.ui.home.HomeScreen
import com.sky.account.manager.ui.login.LoginScreen
import com.sky.account.manager.ui.login.rememberLoginState
import com.sky.account.manager.ui.register.RegisterScreen
import com.sky.account.manager.ui.register.rememberRegisterState
import com.sky.account.manager.ui.splash.SplashScreen
import com.sky.account.manager.ui.view.MessageUI

/**
 * Created by sky on 2021/8/26.
 */
fun main() = application {

    val appState = rememberAppState {
        exitApplication()
    }

    BearWindow(
        onCloseRequest = appState::exit,
        state = appState.windowState
    ) {
        Surface {
            when(appState.appNav) {
                AppNav.SPLASH -> {
                    SplashScreen()
                }
                AppNav.REGISTER -> {
                    RegisterScreen(
                        rememberRegisterState(appState)
                    )
                }
                AppNav.LOGIN -> {
                    LoginScreen(
                        rememberLoginState(appState)
                    )
                }
                AppNav.HOME -> {
                    HomeScreen(appState)
                }
            }
            MessageUI(appState.messageState)
        }
    }
}
