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

package com.sky.account.manager.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.theme.BearTheme

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun PolarBearWindow(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(),
    title: String = stringResource("app.name"),
    content: @Composable () -> Unit
) {
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title
    ) {
        BearTheme(
            content = content
        )
    }
}