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

package com.sky.account.manager.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.ui.common.BearSortTitle
import com.sky.account.manager.ui.common.BearTextMenu
import com.sky.account.manager.ui.common.BearTopBar

/**
 * Created by sky on 2021/11/8.
 */
enum class SetType {
    SETTING, ABOUT
}

@Composable
fun SettingUI(
    appState: AppState
) {
    val setState = remember { mutableStateOf(SetType.SETTING) }

    when(setState.value) {
        SetType.SETTING -> {
            SettingListUI(appState, setState)
        }
        SetType.ABOUT -> {
            AboutUI { setState.value = SetType.SETTING }
        }
    }
}

@Composable
private fun SettingListUI(
    appState: AppState,
    setState: MutableState<SetType>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 50.dp, right = 30.dp, bottom = 30.dp)
    ) {
        Column {
            Spacer(Modifier.height(25.dp))
            BearSortTitle("Account")
            Spacer(Modifier.height(15.dp))
            BearTextMenu(
                title = "Import",
                icon = painterResource("image/ic_arrow_right.svg")
            ) {

            }
            Spacer(Modifier.height(15.dp))
            BearTextMenu(
                title = "Export",
                icon = painterResource("image/ic_arrow_right.svg")
            ) {

            }
            Spacer(Modifier.height(30.dp))
            BearSortTitle("Other")
            Spacer(Modifier.height(15.dp))
            BearTextMenu(
                title = "About",
                icon = painterResource("image/ic_arrow_right.svg")
            ) {
                // 显示关于
                setState.value = SetType.ABOUT
            }
        }
    }
}

@Composable
private fun AboutUI(
    back: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 54.dp, right = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BearTopBar(
            backText = "Setting",
            backIcon = painterResource("image/ic_back.svg"),
            title = "About",
            onBack = back
        )
        Spacer(Modifier.height(40.dp))
        Image(
            painter = painterResource("image/icon.png"),
            contentDescription = null,
            modifier = Modifier.size(130.dp)
        )
        Spacer(Modifier.height(26.dp))
        BearTextMenu(
            title = "Version",
            desc = "1.2.3"
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = "Mail",
            desc = "jingcai.wei@163.com"
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = "Source",
            desc = "https://github.com/sky-wei/kotlin-polarbear"
        )
    }
}