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

package com.sky.account.manager.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.AppState
import com.sky.account.manager.ui.common.PolarBearWindow

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun AppHome(
    appState: AppState,
    onCloseRequest: () -> Unit,
) {
    PolarBearWindow(
        onCloseRequest = onCloseRequest,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = WindowSize(1000.dp, 800.dp)
        )
    ) {
        HomeUI(appState)
    }
}

@Composable
fun HomeUI(
    appState: AppState
) {


}
