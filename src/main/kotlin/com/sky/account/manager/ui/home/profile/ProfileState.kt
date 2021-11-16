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

package com.sky.account.manager.ui.home.profile

import androidx.compose.runtime.*
import com.sky.account.manager.AppState
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ui.ProfileNav
import com.sky.account.manager.ui.base.AbstractState
import com.sky.account.manager.util.CheckUtil
import com.sky.account.manager.util.MD5Util
import kotlinx.coroutines.launch

/**
 * Created by sky on 2021-11-16.
 */
@Composable
fun rememberProfileState(
    appState: AppState
) = remember {
    ProfileState(appState)
}

class ProfileState(
    appState: AppState
) : AbstractState(appState) {

    val admin: AdminItem
        get() = appState.admin
    var profileNav by mutableStateOf(ProfileNav.DISPLAY)
        private set

    /**
     * 修改导航
     */
    fun changeNav(profileNav: ProfileNav) {
        this.profileNav = profileNav
    }

    /**
     * 修改
     */
    fun change(
        item: AdminItem,
        oldPassword: String,
        newPassword: String,
        desc: String
    ) {
        if (!CheckUtil.checkUser(item, oldPassword, newPassword, this::showMessage)) {
            return
        }

        scope.launch {
            repository.load(item.id)
                .doSuccess {
                    repository.update(
                        item.copy(
                            password = MD5Util.md5sum(newPassword),
                            desc = desc
                        ),
                        it.map { account ->
                            // 解密
                            appState.decrypt(account, oldPassword)
                        }.map { account ->
                            // 加密
                            appState.encrypt(account, newPassword)
                        }
                    ).doSuccess {
                        appState.update(
                            item.copy(
                                password = newPassword,
                                desc = desc
                            )
                        )
                        changeNav(ProfileNav.DISPLAY)
                    }.doFailure { tr ->
                        showMessage("${tr.message}")
                    }
                }.doFailure {
                    showMessage("${it.message}")
                }
        }
    }
}