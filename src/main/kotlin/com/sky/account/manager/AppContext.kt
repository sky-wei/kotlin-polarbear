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
import androidx.compose.runtime.remember
import com.sky.account.manager.adapter.Log4JAdapter
import com.sky.account.manager.component.ComponentFactory
import com.sky.account.manager.component.ComponentManager
import com.sky.account.manager.interfaces.IAccountManager
import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IComponentManager
import com.sky.account.manager.util.Alog

/**
 * Created by sky on 2021/8/28.
 */
@Composable
fun rememberAppContext() = remember {
    AppContext()
}

class AppContext : IAppContext {

    private val mComponentManager: IComponentManager

    init {

        Alog.setSingletonInstance(
            Alog.create {
                debug = true
                adapter = Log4JAdapter()
            }
        )

        mComponentManager = ComponentManager.create(this) {
            factory = ComponentFactory()
        }
    }

    fun release() {
        mComponentManager.release()
    }

    override fun getAccountManager(): IAccountManager {
        return mComponentManager[IAccountManager::class.java]
    }

    override fun getComponentManager(): IComponentManager {
        return mComponentManager
    }
}