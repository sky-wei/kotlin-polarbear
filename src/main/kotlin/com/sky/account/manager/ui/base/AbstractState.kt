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

package com.sky.account.manager.ui.base

import com.sky.account.manager.AppState
import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IAppRepository
import kotlinx.coroutines.CoroutineScope

/**
 * Created by sky on 2021-11-16.
 */
abstract class AbstractState(
    val appState: AppState
) {

    val context: IAppContext = appState.context

    val repository: IAppRepository = appState.repository
    val scope: CoroutineScope = appState.scope

    /**
     * 显示消息
     */
    fun showMessage(msg: String) {
        appState.showMessage(msg)
    }
}