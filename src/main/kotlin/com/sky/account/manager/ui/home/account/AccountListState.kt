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

import androidx.compose.runtime.*
import com.sky.account.manager.AppState
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ui.AccountNav
import com.sky.account.manager.ui.base.AbstractState
import com.sky.account.manager.util.CheckUtil
import kotlinx.coroutines.launch

/**
 * Created by sky on 2021-11-16.
 */
@Composable
fun rememberAccountListState(
    appState: AppState
) = remember {
    AccountListState(appState)
}

class AccountListState(
    appState: AppState
) : AbstractState(appState) {

    private val admin: AdminItem
        get() = appState.admin

    var search: String by mutableStateOf("")
        private set
    var accountNav: AccountNav by mutableStateOf(AccountNav.LIST)
        private set
    var refresh: Boolean by mutableStateOf(true)
        private set
    var accounts: List<AccountItem> by mutableStateOf(ArrayList())
        private set
    var account: AccountItem by mutableStateOf(AccountItem.EMPTY)
        private set

    /**
     * 加载账号列表
     */
    fun refreshList() {

        scope.launch {
            // 加载账号
            repository.load(admin.id)
                .doSuccess {
                    refresh = false
                    accounts = it
                }.doFailure {
                    showMessage("${it.message}")
                }
        }
    }

    /**
     * 选择
     */
    fun choose(item: AccountItem) {
        account = appState.decrypt(item)
    }

    /**
     * 搜索
     */
    fun search(keyword: String) {
        search = keyword
        scope.launch {
            if (keyword.isEmpty()) {
                repository.load(admin.id)
            } else {
                repository.search(admin.id, keyword)
            }.doSuccess {
                accounts = it
            }.doFailure {
                showMessage("${it.message}")
            }
        }
    }

    /**
     * 删除账号
     */
    fun delete(item: AccountItem) {
        scope.launch {
            repository.delete(
                appState.encrypt(item)
            ).doSuccess {
                backList()
            }.doFailure {
                showMessage("${it.message}")
            }
        }
    }

    /**
     * 修改账号
     */
    fun change(item: AccountItem) {

        if (!CheckUtil.checkUser(item, this::showMessage)) {
            return
        }

        scope.launch {
            repository.update(
                appState.encrypt(item)
            ).doSuccess {
                backList()
            }.doFailure {
                showMessage("${it.message}")
            }
        }
    }

    /**
     * 修改导航
     */
    fun changeNav(accountNav: AccountNav) {
        this.accountNav = accountNav
    }

    /**
     * 返回列表并刷新
     */
    private fun backList() {
        accountNav = AccountNav.LIST
        refresh = true
    }
}