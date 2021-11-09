package com.sky.account.manager.ui.profile

import androidx.compose.runtime.*
import com.sky.account.manager.AppState

/**
 * Created by sky on 2021-11-09.
 */
enum class ProfileType {
    DISPLAY, EDIT
}

@Composable
fun ProfileUI(
    appState: AppState
) {
    var adminState by remember { mutableStateOf(ProfileType.DISPLAY) }
    val adminItem by remember { mutableStateOf(appState.adminUser()) }

    when(adminState) {
        ProfileType.DISPLAY -> {
            ProfileDisplayUI(adminItem) {
                adminState = ProfileType.EDIT
            }
        }
        ProfileType.EDIT -> {
            ProfileEditUI(appState, adminItem) {
                adminState = ProfileType.DISPLAY
            }
        }
    }
}