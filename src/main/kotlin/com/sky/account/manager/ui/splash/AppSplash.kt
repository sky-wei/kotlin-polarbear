package com.sky.account.manager.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.AppState
import com.sky.account.manager.ui.theme.PolarBearTheme

/**
 * Created by sky on 2021-11-01.
 */
@Composable
fun AppSplash(
    appState: AppState,
    onCloseRequest: () -> Unit,
) {
    Window(
        onCloseRequest = onCloseRequest,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = WindowSize(900.dp, 260.dp)
        ),
        undecorated = true
    ) {
        PolarBearTheme {
            SplashUI()
        }
    }
}

@Composable
fun SplashUI() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            "Account Manager",
            Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold,
            fontSize = 100.sp
        )
    }
}