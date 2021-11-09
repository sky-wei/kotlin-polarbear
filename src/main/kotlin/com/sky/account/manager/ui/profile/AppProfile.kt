package com.sky.account.manager.ui.profile

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
import com.sky.account.manager.util.Alog

/**
 * Created by sky on 2021-11-09.
 */
@Composable
fun ProfileUI(
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

            var userName by remember { mutableStateOf(appState.adminUser().name) }
            var userPassword by remember { mutableStateOf("") }
            var userNewPassword by remember { mutableStateOf("") }
            var userDesc by remember { mutableStateOf(appState.adminUser().desc) }

            BearBigTitle("Admin")
            Spacer(Modifier.height(40.dp))

            BearEditText(
                icon = "image/ic_user.svg",
                label = stringResource("label.userName"),
                readOnly = true,
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
                icon = "image/ic_password.svg",
                label = "NewPassword",
                value = userNewPassword,
                visualTransformation = PasswordVisualTransformation(),
                keyboardType = KeyboardType.Password
            ) {
                userNewPassword = it
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
                text = "Change"
            ) {
                // 修改

            }
        }
    }
}