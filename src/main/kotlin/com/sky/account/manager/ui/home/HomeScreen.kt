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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.XConstant
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.HomeNav
import com.sky.account.manager.ui.home.account.AccountListScreen
import com.sky.account.manager.ui.home.account.rememberAccountListState
import com.sky.account.manager.ui.home.create.NewAccountScreen
import com.sky.account.manager.ui.home.create.rememberNewAccountState
import com.sky.account.manager.ui.home.profile.ProfileScreen
import com.sky.account.manager.ui.home.profile.rememberProfileState
import com.sky.account.manager.ui.home.setting.SettingScreen
import com.sky.account.manager.ui.home.setting.rememberSettingState
import com.sky.account.manager.ui.theme.GrayText

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun HomeScreen(
    appState: AppState
) {
    var homeNav by remember { mutableStateOf(HomeNav.LIST) }

    Row {
        HomeSideBar(homeNav) {
            homeNav = it
            appState.cleanMessage()
        }
        HomeFrameContent(appState, homeNav)
    }
}

@Composable
fun HomeSideBar(
    homeNav: HomeNav,
    onNavChange: (homeNav: HomeNav) -> Unit
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
            SideBarTitle(
                painterResource("image/icon.png"),
                stringResource("label.accountManage")
            )

            Spacer(Modifier.height(40.dp))
            SideBarNavItem(
                stringResource("label.home"),
                painterResource("image/ic_home.svg"),
                homeNav == HomeNav.LIST
            ) {
                onNavChange(HomeNav.LIST)
            }

            Spacer(Modifier.height(10.dp))
            SideBarNavItem(
                stringResource("label.profile"),
                painterResource("image/ic_profile.svg"),
                homeNav == HomeNav.PROFILE
            ) {
                onNavChange(HomeNav.PROFILE)
            }

            Spacer(Modifier.height(10.dp))
            SideBarNavItem(
                stringResource("label.new"),
                painterResource("image/ic_create.svg"),
                homeNav == HomeNav.NEW
            ) {
                onNavChange(HomeNav.NEW)
            }

            Spacer(Modifier.height(10.dp))
            SideBarNavItem(
                stringResource("label.settings"),
                painterResource("image/ic_settings.svg"),
                homeNav == HomeNav.SETTING
            ) {
                onNavChange(HomeNav.SETTING)
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
fun SideBarTitle(
    icon: Painter,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(58.dp)
        )
        Spacer(Modifier.width(15.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold
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
fun HomeFrameContent(
    appState: AppState,
    homeNav: HomeNav
) {
    Crossfade(targetState = homeNav) {
        when(it) {
            HomeNav.LIST -> {
                AccountListScreen(
                    rememberAccountListState(appState)
                )
            }
            HomeNav.PROFILE -> {
                ProfileScreen(
                    rememberProfileState(appState)
                )
            }
            HomeNav.NEW -> {
                NewAccountScreen(
                    rememberNewAccountState(appState)
                )
            }
            HomeNav.SETTING -> {
                SettingScreen(
                    rememberSettingState(appState)
                )
            }
        }
    }
}
