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

package com.sky.account.manager.interfaces

import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.data.model.XResult

/**
 * Created by sky on 2021/11/1.
 */
interface IAppRepository : IComponent {

    /**
     * 是否需要注册
     */
    suspend fun isRegister(): Boolean

    /**
     * 注册管理账号
     */
    suspend fun register(item: AdminItem): XResult<AdminItem>

    /**
     * 登录
     */
    suspend fun login(item: AdminItem): XResult<AdminItem>

    /**
     * 更新账号
     */
    suspend fun update(item: AdminItem): XResult<AdminItem>

    /**
     * 清除数据
     */
    suspend fun clearData(adminId: Int): XResult<Boolean>

    /**
     * 加载账号
     */
    suspend fun load(adminId: Int): XResult<List<AccountItem>>

    /**
     * 搜索账号
     */
    suspend fun search(adminId: Int, keyword: String): XResult<List<AccountItem>>

    /**
     * 创建账号
     */
    suspend fun create(item: AccountItem): XResult<AccountItem>

    /**
     * 创建账号
     */
    suspend fun create(items: List<AccountItem>): XResult<List<AccountItem>>

    /**
     * 更新账号
     */
    suspend fun update(item: AccountItem): XResult<AccountItem>

    /**
     * 更新账号
     */
    suspend fun update(
        item: AdminItem,
        items: List<AccountItem>
    ): XResult<List<AccountItem>>

    /**
     * 删除账号
     */
    suspend fun delete(item: AccountItem): XResult<AccountItem>
}