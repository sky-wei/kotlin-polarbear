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

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.ui.common.BearMenu

/**
 * Created by sky on 2021/11/8.
 */

@Composable
fun SettingUI(
    appState: AppState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 50.dp, right = 30.dp, bottom = 30.dp)
    ) {
        Column {
            Text(
                text = "Setting",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.absolutePadding(left = 28.dp)
            )
            Spacer(Modifier.height(15.dp))
            BearMenu(
                title = "Import",
                icon = painterResource("image/ic_create.svg")
            ) {

            }
            Spacer(Modifier.height(15.dp))
            BearMenu(
                title = "Export",
                icon = painterResource("image/ic_create.svg")
            ) {

            }
            Spacer(Modifier.height(15.dp))
            BearMenu(
                title = "About",
                icon = painterResource("image/ic_create.svg")
            ) {

            }
        }
    }
}