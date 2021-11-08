package com.sky.account.manager.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.ui.theme.Foreground
import com.sky.account.manager.ui.theme.ToastBackground

/**
 * Created by sky on 2021-11-03.
 */
enum class ToastDuration(val value: Int) {
    Short(1000), Long(3000)
}

private val message = mutableStateOf("")

fun message(): String {
    return message.value
}

/**
 * 清除消息
 */
fun cleanMessage() {
    message.value = ""
}

/**
 * 显示消息
 */
fun showMessage(text: String) {
    message.value = text
}

@Composable
fun MessageUI(
    text: String = message()
) {

    if (text.isEmpty()) return

    Box(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            modifier = Modifier.defaultMinSize(320.dp, 70.dp),
            color = ToastBackground,
            shape = RoundedCornerShape(6.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = text,
                    color = Foreground
                )
            }

            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    modifier = Modifier.padding(10.dp).size(20.dp).clickable { cleanMessage() },
                    alignment = Alignment.TopEnd,
                    painter = painterResource("image/ic_close.svg"),
                    contentDescription = null
                )
            }
        }
    }
}