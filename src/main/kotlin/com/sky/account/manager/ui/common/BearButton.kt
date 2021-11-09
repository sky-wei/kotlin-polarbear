package com.sky.account.manager.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by sky on 2021-11-01.
 */
@Composable
fun BigBearButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.size(300.dp, 58.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

@Composable
fun BearIconText(
    icon: Painter,
    text: String,
    color: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .absolutePadding(left = 10.dp, top = 8.dp, bottom = 8.dp, right = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = text,
            color = color
        )
    }
}