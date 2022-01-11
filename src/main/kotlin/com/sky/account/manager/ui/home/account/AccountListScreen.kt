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

package com.sky.account.manager.ui.home.account

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.ex.stringResource
import com.sky.account.manager.ui.AccountNav
import com.sky.account.manager.ui.common.BearSearch
import com.sky.account.manager.ui.theme.GrayText

/**
 * Created by sky on 2021-11-09.
 */
@Composable
fun AccountListScreen(
    accountListState: AccountListState,
    onNewAccount: () -> Unit,
) {
    when(accountListState.accountNav) {
        AccountNav.LIST -> {
            if (accountListState.refresh) {
                // 刷新列表
                accountListState.refreshList()
            }
            AccountList(
                search = accountListState.search,
                emptyList = accountListState.emptyList,
                accounts = accountListState.accounts,
                onSearch = accountListState::search,
                onNewAccount = onNewAccount
            ) {
                accountListState.choose(it)
                accountListState.changeNav(AccountNav.DISPLAY)
            }
        }
        AccountNav.DISPLAY -> {
            DisplayAccountScreen(
                item = accountListState.account,
                onBack = { accountListState.changeNav(AccountNav.LIST) },
                onEdit = { accountListState.changeNav(AccountNav.EDIT) },
                onDelete = { accountListState.delete(accountListState.account) }
            )
        }
        AccountNav.EDIT -> {
            EditAccountScreen(
                item = accountListState.account,
                onBack = { accountListState.changeNav(AccountNav.DISPLAY) },
                onChange = accountListState::change
            )
        }
    }
}

@Composable
fun AccountList(
    search: String,
    emptyList: Boolean,
    accounts: List<AccountItem>,
    onSearch: (keyword: String) -> Unit,
    onNewAccount: () -> Unit,
    onClick: (item: AccountItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 50.dp, right = 30.dp, bottom = 30.dp)
    ) {
        Column {
            BearSearch(
                icon = painterResource("image/ic_search.svg"),
                label = stringResource("app.search"),
                value = search
            ) {
                onSearch(it)
            }
            Spacer(Modifier.height(30.dp))
            Box {
                val state = rememberLazyListState()
                LazyColumn(
                    state = state,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(accounts) { value ->
                        AccountItem(value) { onClick(value) }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    adapter = rememberScrollbarAdapter(state)
                )
            }
        }

        if (emptyList) {
            Box(
                modifier = Modifier
                    .clickable { onNewAccount() }
                    .padding(18.dp)
                    .align(Alignment.Center)
            ) {
                AccountEmpty()
            }
        }
    }
}

@Composable
fun AccountItem(
    item: AccountItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.onPrimary,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick.invoke() }
            .absolutePadding(left = 28.dp, top = 20.dp, right = 28.dp, bottom = 20.dp),
    ) {
        Column {
            Text(
                text = item.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = stringResource("label.urlX", item.url),
                style = MaterialTheme.typography.body2,
                color = GrayText
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = stringResource("label.descX", item.desc),
                style = MaterialTheme.typography.body2,
                color = GrayText
            )
        }

        Icon(
            painter = painterResource("image/ic_arrow_right.svg"),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(20.dp).align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun AccountEmpty() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource("image/ic_empty.svg"),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.size(88.dp)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = stringResource("label.newAccount"),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.absolutePadding(top = 4.dp)
        )
    }
}