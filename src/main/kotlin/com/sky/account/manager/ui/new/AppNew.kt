package com.sky.account.manager.ui.new

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.common.BearBigTitle
import com.sky.account.manager.ui.common.BearEditText
import com.sky.account.manager.ui.common.BigBearButton

/**
 * Created by sky on 2021-11-09.
 */
@Composable
fun NewUI(
    appState: AppState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 50.dp, right = 30.dp, bottom = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var userName by remember { mutableStateOf("") }
            var userPassword by remember { mutableStateOf("") }
            var userUrl by remember { mutableStateOf("") }
            var userDesc by remember { mutableStateOf("") }

            BearBigTitle("Account")
            Spacer(Modifier.height(40.dp))

            BearEditText(
                icon = "image/ic_user.svg",
                label = stringResource("label.userName"),
                value = userName
            ) {
                userName = it
            }

            Spacer(Modifier.height(20.dp))

            BearEditText(
                icon = "image/ic_password.svg",
                label = stringResource("label.password"),
                value = userPassword,
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password
            ) {
                userPassword = it
            }

            Spacer(Modifier.height(20.dp))

            BearEditText(
                icon = "image/ic_url.svg",
                label = "Url",
                value = userUrl,
            ) {
                userUrl = it
            }

            Spacer(Modifier.height(20.dp))

            BearEditText(
                icon = "image/ic_desc.svg",
                label = "Description",
                value = userDesc,
                singleLine = false,
                maxLines = 5
            ) {
                userDesc = it
            }

            Spacer(Modifier.height(40.dp))

            BigBearButton(
                text = "Create"
            ) {
                // 创建

            }
        }
    }
}