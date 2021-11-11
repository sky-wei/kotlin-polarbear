/*
 * Copyright (c) 2021 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.account.manager.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.sky.account.manager.AppState
import com.sky.account.manager.XConstant
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.HomeNav
import com.sky.account.manager.ui.common.BearWindow
import com.sky.account.manager.ui.new.NewUI
import com.sky.account.manager.ui.profile.ProfileUI
import com.sky.account.manager.ui.setting.SettingUI
import com.sky.account.manager.ui.theme.GrayText

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun AppHome(
    appState: AppState,
    onCloseRequest: () -> Unit,
) {
    BearWindow(
        onCloseRequest = onCloseRequest,
        state = rememberWindowState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(1000.dp, 800.dp)
        )
    ) {
        HomeUI(appState)
    }
}

@Composable
fun HomeUI(
    appState: AppState
) {
    val navItemState = rememberSaveable { mutableStateOf(HomeNav.LIST) }

    Row {
        BearSideBar(appState, navItemState)
        BearContent(appState, navItemState.value)
    }
}

@Composable
fun BearSideBar(
    appState: AppState,
    navItemState: MutableState<HomeNav>
) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colors.onPrimary)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Spacer(Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource("image/icon.png"),
                    contentDescription = null,
                    modifier = Modifier.size(58.dp)
                )
                Spacer(Modifier.width(15.dp))
                Text(
                    text = stringResource("label.accountManager"),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(40.dp))
            SideBarNavItem(
                stringResource("label.home"),
                painterResource("image/ic_home.svg"),
                navItemState.value == HomeNav.LIST
            ) {
                navItemState.value = HomeNav.LIST
            }
            Spacer(Modifier.height(10.dp))
            SideBarNavItem(
                stringResource("label.profile"),
                painterResource("image/ic_profile.svg"),
                navItemState.value == HomeNav.PROFILE
            ) {
                navItemState.value = HomeNav.PROFILE
            }
            Spacer(Modifier.height(10.dp))
            SideBarNavItem(
                stringResource("label.new"),
                painterResource("image/ic_create.svg"),
                navItemState.value == HomeNav.NEW
            ) {
                navItemState.value = HomeNav.NEW
            }
            Spacer(Modifier.height(10.dp))
            SideBarNavItem(
                stringResource("label.settings"),
                painterResource("image/ic_settings.svg"),
                navItemState.value == HomeNav.SETTING
            ) {
                navItemState.value = HomeNav.SETTING
            }
        }

        Text(
            text = stringResource("label.versionX", XConstant.Version.Name),
            color = GrayText,
            modifier = Modifier.align(Alignment.BottomCenter),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun SideBarNavItem(
    title: String,
    icon: Painter,
    selected: Boolean,
    onClick: () -> Unit
) {
    val animatedBackgroundColor by animateColorAsState(
        if (selected) MaterialTheme.colors.secondary else MaterialTheme.colors.background
    )
    val animatedContentColor by animateColorAsState(
        if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = animatedBackgroundColor,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick.invoke() }
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = animatedContentColor,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(
            title,
            style = MaterialTheme.typography.body1,
            color = animatedContentColor,
            modifier = Modifier.absolutePadding(top = 4.dp)
        )
    }
}

@Composable
fun BearContent(
    appState: AppState,
    homeNav: HomeNav
) {
    Crossfade(targetState = homeNav) {
        when(it) {
            HomeNav.LIST -> {
                HomeContentUI(appState)
            }
            HomeNav.PROFILE -> {
                ProfileUI(appState)
            }
            HomeNav.NEW -> {
                NewUI(appState)
            }
            HomeNav.SETTING -> {
                SettingUI(appState)
            }
        }
    }
}
