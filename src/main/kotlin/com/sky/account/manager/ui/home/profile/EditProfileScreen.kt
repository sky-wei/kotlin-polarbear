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

package com.sky.account.manager.ui.home.profile

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.common.BearEditText
import com.sky.account.manager.ui.common.BearTopBar
import com.sky.account.manager.ui.common.BigBearButton

/**
 * Created by sky on 2021/11/9.
 */
@Composable
fun EditProfileScreen(
    admin: AdminItem,
    onBack: () -> Unit,
    onChange: (item: AdminItem, oldPassword: String, newPassword: String, desc: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 54.dp, right = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BearTopBar(
            backText = stringResource("label.admin"),
            backIcon = painterResource("image/ic_back.svg"),
            title = stringResource("label.edit"),
            onBack = onBack
        )
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            EditProfileContent(admin, onChange)
        }
    }
}

@Composable
fun EditProfileContent(
    item: AdminItem,
    onChange: (item: AdminItem, oldPassword: String, newPassword: String, desc: String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var name by remember { mutableStateOf(item.name) }
        var password by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var desc by remember { mutableStateOf(item.desc) }

        BearEditText(
            icon = painterResource("image/ic_user.svg"),
            label = stringResource("label.userName"),
            readOnly = true,
            value = name
        ) {
            name = it
        }

        Spacer(Modifier.height(20.dp))

        BearEditText(
            icon = painterResource("image/ic_password.svg"),
            label = stringResource("label.password"),
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password
        ) {
            password = it
        }

        Spacer(Modifier.height(20.dp))

        BearEditText(
            icon = painterResource("image/ic_password.svg"),
            label = stringResource("label.newPassword"),
            value = newPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password
        ) {
            newPassword = it
        }

        Spacer(Modifier.height(20.dp))

        BearEditText(
            icon = painterResource("image/ic_desc.svg"),
            label = stringResource("label.desc"),
            value = desc,
            singleLine = false,
            maxLines = 5
        ) {
            desc = it
        }

        Spacer(Modifier.height(40.dp))

        BigBearButton(stringResource("label.change")) {
            // ??????
            onChange(item, password, newPassword, desc)
        }
    }
}