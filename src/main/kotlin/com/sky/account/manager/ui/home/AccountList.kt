package com.sky.account.manager.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.account.manager.AppState
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.ui.common.BearSearch
import com.sky.account.manager.ui.theme.GrayText

/**
 * Created by sky on 2021-11-09.
 */
enum class AccountType {
    LIST, DISPLAY, EDIT, DELETE
}


@Composable
fun HomeContentUI(
    appState: AppState
) {
    val accountState = remember { mutableStateOf(AccountType.LIST) }
    val accountItem = remember { mutableStateOf(AccountItem.valueOf(0, "", "")) }

    when(accountState.value) {
        AccountType.LIST -> {
            AccountListUI(appState) {
                accountItem.value = it
                accountState.value = AccountType.DISPLAY
            }
        }
        AccountType.DISPLAY -> {
            AccountDisplay(
                item = accountItem.value,
                onBack = { accountState.value = AccountType.LIST },
                onEdit = { accountState.value = AccountType.EDIT },
                onDelete = {  }
            )
        }
        AccountType.EDIT -> {
            AccountEditUI(appState, accountItem.value) {
                accountState.value = AccountType.DISPLAY
            }
        }
    }
}

@Composable
fun AccountListUI(
    appState: AppState,
    onClick: (item: AccountItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(left = 30.dp, top = 50.dp, right = 30.dp, bottom = 30.dp)
    ) {

        var search by remember { mutableStateOf("") }

        Column {
            BearSearch(
                icon = painterResource("image/ic_search.svg"),
                label = "Search",
                value = search
            ) {
                search = it
            }
            Spacer(Modifier.height(30.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(appState.accounts()) { value ->
                    AccountItemUI(value) { onClick(value) }
                }
            }
        }
    }
}



@Composable
fun AccountItemUI(
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
                text = "Url: ${item.url}",
                style = MaterialTheme.typography.body2,
                color = GrayText
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Description: ${item.desc}",
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