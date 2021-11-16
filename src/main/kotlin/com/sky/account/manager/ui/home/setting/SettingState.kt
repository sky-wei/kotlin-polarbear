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

package com.sky.account.manager.ui.home.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sky.account.manager.AppState
import com.sky.account.manager.DialogState
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.doFailure
import com.sky.account.manager.ex.doSuccess
import com.sky.account.manager.ex.fromJson
import com.sky.account.manager.ui.base.AbstractState
import com.sky.account.manager.util.Alog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Created by sky on 2021-11-16.
 */
@Composable
fun rememberSettingState(
    appState: AppState
) = remember {
    SettingState(appState)
}

class SettingState(
    appState: AppState
) : AbstractState(appState) {

    private val admin: AdminItem
        get() = appState.admin

    val importDialog = DialogState<File?>()
    val exportDialog = DialogState<File?>()

    /**
     * 导入
     */
    fun import() {
        scope.launch {
            delay(200)
            withContext(Dispatchers.IO) {
                importDialog
                    .awaitResult()
                    ?.run { import(this) }
            }
        }
    }

    /**
     * 导出
     */
    fun export() {
        scope.launch {
            delay(200)
            withContext(Dispatchers.IO) {
                exportDialog
                    .awaitResult()
                    ?.run { export(this) }
            }
        }
    }

    /**
     * 清除数据
     */
    fun clearData() {
        scope.launch {
            withContext(Dispatchers.IO) {
                repository
                    .clearData(admin.id)
                    .doSuccess {
                        showMessage("清除数据完成！")
                    }
                    .doFailure {
                        showMessage("${it.message}")
                    }
            }
        }
    }

    /**
     * 导入
     */
    private suspend fun import(file: File) {
        try {
            var count = 0
            repository.create(
                Gson().fromJson<List<AccountItem>>(
                    file.readText()
                ).sortedBy {
                    it.id
                }.map {
                    appState.encrypt(it).copy(
                        id = 0,
                        adminId = admin.id,
                        createTime = System.currentTimeMillis() + (count++)
                    )
                }
            ).doSuccess {
                showMessage("导入成功！")
            }.doFailure {
                showMessage("${it.message}")
            }
        } catch (tr: Throwable) {
            showMessage(tr.message!!)
            Alog.e("处理异常", tr)
        }
    }

    /**
     * 导出
     */
    private suspend fun export(file: File) {
        try {
            repository.load(admin.id)
                .doSuccess {
                    file.writeText(
                        GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(
                                it.map { item -> appState.decrypt(item) }
                            )
                    )
                    showMessage("导出成功！")
                }
                .doFailure {
                    showMessage("${it.message}")
                }
        } catch (tr: Throwable) {
            showMessage(tr.message!!)
            Alog.e("处理异常", tr)
        }
    }
}