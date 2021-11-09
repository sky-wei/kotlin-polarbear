package com.sky.account.manager.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.common.BearTextMenu
import com.sky.account.manager.ui.common.BearTopBar

/**
 * Created by sky on 2021-11-09.
 */
@Composable
fun AccountDisplay(
    appState: AppState,
    item: AccountItem,
    back: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 54.dp, right = 30.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BearTopBar(
            back = "Home",
            backIcon = painterResource("image/ic_back.svg"),
            title = "Account",
            click = back
        )
        Spacer(Modifier.height(40.dp))
        BearTextMenu(
            title = stringResource("label.userName"),
            desc = item.name
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = stringResource("label.password"),
            desc = item.password
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = "Url",
            desc = item.url
        )
        Spacer(Modifier.height(15.dp))
        BearTextMenu(
            title = "Description",
            desc = item.desc
        )
    }
}