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

package com.sky.account.manager

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.getAppRepository
import com.sky.account.manager.interfaces.IAppRepository
import com.sky.account.manager.ui.AppNav
import com.sky.account.manager.util.SecretUtil
import kotlinx.coroutines.*

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun rememberAppState(onAppExit: () -> Unit) = remember {
    AppState(AppContext(), onAppExit)
}

class AppState(
    val context: AppContext,
    private val onAppExit: () -> Unit
) {

    val repository: IAppRepository by lazy { context.getAppRepository() }
    val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    val windowState = WindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(1000.dp, 800.dp)
    )

    val messageState = MessageState()

    var loading: Boolean by mutableStateOf(false)
        private set
    var appNav: AppNav by mutableStateOf(AppNav.SPLASH)
        private set
    var admin: AdminItem by mutableStateOf(AdminItem.EMPTY)
        private set

    init {
        initData()
    }

    /**
     * 初始化数据
     */
    private fun initData() {

        scope.launch {

            val isRegister = repository.isRegister()

            delay(800)

            appNav = if (isRegister) {
                AppNav.REGISTER
            } else {
                AppNav.LOGIN
            }
        }
    }

    /**
     * 更新
     */
    fun update(item: AdminItem) {
        this.admin = item
    }

    /**
     * 退出
     */
    fun exit() {
        context.release()
        scope.cancel()
        onAppExit()
    }

    /**
     * 加密
     */
    fun encrypt(
        item: AccountItem,
        password: String = admin.password
    ): AccountItem {
        return item.copy(
            password = SecretUtil.encrypt(password, item.password)
        )
    }

    /**
     * 解密
     */
    fun decrypt(
        item: AccountItem,
        password: String = admin.password
    ): AccountItem {
        return item.copy(
            password = SecretUtil.decrypt(password, item.password)
        )
    }

    /**
     * 切换到Home
     */
    fun toHome(admin: AdminItem) {
        cleanMessage()
        update(admin)
        appNav = AppNav.HOME
    }

    /**
     * 显示消息
     */
    fun showMessage(msg: String) {
        messageState.show(msg)
    }

    /**
     * 清除消息
     */
    fun cleanMessage() {
        messageState.clean()
    }
}

/**
 * Dialog状态
 */
class DialogState<T> {

    private var onResult: CompletableDeferred<T>? by mutableStateOf(null)

    val isAwaiting get() = onResult != null

    /**
     * 等待结果
     */
    suspend fun awaitResult(): T {
        onResult = CompletableDeferred()
        val result = onResult!!.await()
        onResult = null
        return result
    }

    /**
     * 返回结果
     */
    fun onResult(result: T) = onResult!!.complete(result)
}

/**
 * 消息状态
 */
class MessageState {

    var message: String? by mutableStateOf(null)
        private set

    /**
     * 清除消息
     */
    fun clean() {
        message = null
    }

    /**
     * 显示消息
     */
    fun show(msg: String) {
        message = msg
    }
}