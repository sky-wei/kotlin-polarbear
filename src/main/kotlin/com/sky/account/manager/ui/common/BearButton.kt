package com.sky.account.manager.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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