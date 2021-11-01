package com.sky.account.manager.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Created by sky on 2021-11-01.
 */
@Composable
fun BearEditText(
    icon: String,
    label: String = "",
    value: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        modifier = Modifier.width(280.dp),
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon = {
            Image(
                modifier = Modifier.size(30.dp, 30.dp),
                painter = painterResource(icon),
                contentDescription = label
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        )
    )
}