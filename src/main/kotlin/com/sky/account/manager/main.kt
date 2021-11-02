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

import androidx.compose.ui.window.application
import com.sky.account.manager.ui.NavType
import com.sky.account.manager.ui.home.AppHome
import com.sky.account.manager.ui.login.AppLogin
import com.sky.account.manager.ui.register.AppRegister
import com.sky.account.manager.ui.splash.AppSplash

/**
 * Created by sky on 2021/8/26.
 */
fun main() = application {

    val appState = rememberAppState()

    when(appState.navType()) {
        NavType.SPLASH -> {
            AppSplash(appState) {
                exitApplication()
            }
        }
        NavType.REGISTER -> {
            AppRegister(appState) {
                exitApplication()
            }
        }
        NavType.LOGIN -> {
            AppLogin(appState) {
                exitApplication()
            }
        }
        NavType.HOME -> {
            AppHome(appState) {
                exitApplication()
            }
        }
    }
}
