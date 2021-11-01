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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sky.account.manager.ex.getAppRepository
import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IAppRepository
import com.sky.account.manager.ui.NavType

/**
 * Created by sky on 2021/10/31.
 */
@Composable
fun rememberAppState() = remember {
    AppState()
}

class AppState {

    private val _context: IAppContext by lazy { AppContext() }
    private val _repository: IAppRepository by lazy { _context.getAppRepository() }

    private val _navType = mutableStateOf(NavType.LOGIN)

    var navType: NavType
        get() = _navType.value
        set(value) { _navType.value = value }

    init {


    }
}