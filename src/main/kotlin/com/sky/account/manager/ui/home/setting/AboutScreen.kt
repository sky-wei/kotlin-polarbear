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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.XConstant
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.common.BearTextMenu
import com.sky.account.manager.ui.common.BearTopBar

/**
 * Created by sky on 2021/11/15.
 */
@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 54.dp, right = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BearTopBar(
            backText = stringResource("label.settings"),
            backIcon = painterResource("image/ic_back.svg"),
            title = stringResource("label.about"),
            onBack = onBack
        )
        Spacer(Modifier.height(40.dp))
        Image(
            painter = painterResource("image/icon.png"),
            contentDescription = null,
            modifier = Modifier.size(130.dp)
        )
        Spacer(Modifier.height(26.dp))
        BearTextMenu(
            title = stringResource("label.version"),
            desc = XConstant.Version.Name
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = stringResource("label.mail"),
            desc = "jingcai.wei@163.com"
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = stringResource("label.source"),
            desc = "https://github.com/sky-wei/kotlin-polarbear"
        )
    }
}