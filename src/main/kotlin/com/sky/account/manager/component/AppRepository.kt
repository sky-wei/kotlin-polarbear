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

package com.sky.account.manager.component

import com.sky.account.manager.base.AbstractComponent
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.data.model.XResult
import com.sky.account.manager.ex.runOfResult
import com.sky.account.manager.interfaces.IAccountManager
import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IAppRepository

/**
 * Created by sky on 2021/11/1.
 */
class AppRepository(
    context: IAppContext
) : AbstractComponent(context), IAppRepository {

    private val mAccountManager: IAccountManager by lazy { context.getAccountManager() }

    override suspend fun isRegister(): Boolean {
        return !mAccountManager.existAdmin()
    }

    override suspend fun register(item: AdminItem): XResult<AdminItem> {
        return runOfResult { mAccountManager.create(item) }
    }
}