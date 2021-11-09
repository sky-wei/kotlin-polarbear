package com.sky.account.manager.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
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
    readOnly: Boolean = false,
    value: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.width(300.dp),
        readOnly = readOnly,
        onValueChange = onValueChange,
        singleLine = singleLine,
        maxLines = maxLines,
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

@Composable
fun BearSearch(
    icon: Painter,
    label: String = "",
    value: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon = {
            Image(
                modifier = Modifier.size(30.dp, 30.dp),
                painter = icon,
                contentDescription = label
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Search
        )
    )
}