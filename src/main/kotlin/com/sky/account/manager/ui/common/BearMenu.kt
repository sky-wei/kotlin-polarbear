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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.sky.account.manager.ui.theme.GrayText

/**
 * Created by sky on 2021/11/8.
 */

@Composable
fun BearTextMenu(
    title: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick.invoke() }
            .absolutePadding(left = 28.dp, top = 24.dp, right = 28.dp, bottom = 24.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.CenterStart),
            color = MaterialTheme.colors.onSurface
        )

        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(20.dp).align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun BearTextMenu(
    title: String,
    desc: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(6.dp)
            )
            .absolutePadding(left = 28.dp, top = 24.dp, right = 28.dp, bottom = 24.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.CenterStart),
            color = MaterialTheme.colors.onSurface
        )

        Text(
            text = desc,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = GrayText
        )
    }
}