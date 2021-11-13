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

package com.sky.account.manager.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.ui.theme.Foreground
import com.sky.account.manager.ui.theme.ToastBackground

/**
 * Created by sky on 2021-11-03.
 */
@Composable
fun MessageUI(
    appState: AppState
) {

    val message = appState.message?: return

    Box(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.defaultMinSize(320.dp, 70.dp),
            color = ToastBackground,
            shape = RoundedCornerShape(6.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = message,
                    color = Foreground
                )
            }

            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier.padding(10.dp).size(20.dp).clickable { appState.cleanMessage() },
                    alignment = Alignment.TopEnd,
                    painter = painterResource("image/ic_close.svg"),
                    contentDescription = null
                )
            }
        }
    }
}