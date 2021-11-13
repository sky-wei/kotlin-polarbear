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
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.common.*

/**
 * Created by sky on 2021/11/9.
 */
@Composable
fun ProfileDisplayUI(
    item: AdminItem,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 54.dp, right = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BearSubTitle(stringResource("label.admin"))
        Spacer(Modifier.height(40.dp))
        BearTextMenu(
            title = stringResource("label.userName"),
            desc = item.name
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = stringResource("label.password"),
            desc = "******"// item.password
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = stringResource("label.desc"),
            desc = item.desc
        )
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier.align(Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BearIconText(
                icon = painterResource("image/ic_edit.svg"),
                text = stringResource("label.edit"),
                color = MaterialTheme.colors.onSurface,
                onClick = onEdit
            )
        }
    }
}