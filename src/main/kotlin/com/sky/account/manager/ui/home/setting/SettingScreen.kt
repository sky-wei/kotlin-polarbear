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

package com.sky.account.manager.ui.home.setting

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.SetNav
import com.sky.account.manager.ui.common.BearSortTitle
import com.sky.account.manager.ui.common.BearTextMoreMenu
import com.sky.account.manager.ui.view.FileDialog

/**
 * Created by sky on 2021/11/8.
 */
@Composable
fun SettingScreen(
    settingState: SettingState
) {
    var setNavState by remember { mutableStateOf(SetNav.LIST) }

    when(setNavState) {
        SetNav.LIST -> {
            SettingScreen(settingState) {
                setNavState = it
            }
        }
        SetNav.ABOUT -> {
            AboutScreen { setNavState = SetNav.LIST }
        }
    }
}

@Composable
private fun SettingScreen(
    settingState: SettingState,
    onNavChange: (setNav: SetNav) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 50.dp, right = 30.dp, bottom = 30.dp)
    ) {
        Column {
            Spacer(Modifier.height(25.dp))
            BearSortTitle(stringResource("label.account"))

            Spacer(Modifier.height(15.dp))
            BearTextMoreMenu(
                title = stringResource("label.import"),
            ) {
                settingState.import()
            }

            Spacer(Modifier.height(15.dp))
            BearTextMoreMenu(
                title = stringResource("label.export"),
            ) {
                settingState.export()
            }

            Spacer(Modifier.height(30.dp))
            BearSortTitle(stringResource("label.storage"))

            Spacer(Modifier.height(15.dp))
            BearTextMoreMenu(
                title = stringResource("label.clearData"),
            ) {
                settingState.clearData()
            }

            Spacer(Modifier.height(30.dp))
            BearSortTitle(stringResource("label.other"))

            Spacer(Modifier.height(15.dp))
            BearTextMoreMenu(
                title = stringResource("label.about"),
            ) {
                // 显示关于
                onNavChange(SetNav.ABOUT)
            }
        }
    }

    if (settingState.importDialog.isAwaiting) {
        FileDialog(
            title = stringResource("label.import"),
            onResult = {
                settingState.importDialog.onResult(it)
            }
        )
    }

    if (settingState.exportDialog.isAwaiting) {
        FileDialog(
            title = stringResource("label.export"),
            isLoad = false,
            onResult = {
                settingState.exportDialog.onResult(it)
            }
        )
    }
}