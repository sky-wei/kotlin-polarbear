package com.sky.account.manager.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
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
        modifier = Modifier.size(280.dp, 50.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}