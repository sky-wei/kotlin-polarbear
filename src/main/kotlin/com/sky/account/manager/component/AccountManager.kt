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

import com.j256.ormlite.dao.Dao
import com.sky.account.manager.base.AbstractComponent
import com.sky.account.manager.data.disk.entity.AccountEntity
import com.sky.account.manager.data.disk.entity.AdminEntity
import com.sky.account.manager.data.disk.mapper.AccountMapper
import com.sky.account.manager.data.disk.mapper.AdminMapper
import com.sky.account.manager.data.model.AccountItem
import com.sky.account.manager.data.model.AdminItem
import com.sky.account.manager.ex.getDataBaseManager
import com.sky.account.manager.interfaces.IAccountManager
import com.sky.account.manager.interfaces.IAppContext

/**
 * Created by sky on 2021/10/31.
 */
class AccountManager(
    builder: Builder
) : AbstractComponent(builder.context), IAccountManager {

    private val adminDao: Dao<AdminEntity, Int> by lazy {
        context.getDataBaseManager().getAdminDao()
    }
    private val accountDao: Dao<AccountEntity, Int> by lazy {
        context.getDataBaseManager().getAccountDao()
    }

    override fun existAdmin(): Boolean {
        return adminDao.queryForAll().isNotEmpty()
    }

    override fun create(item: AdminItem): Boolean {

        val admins = adminDao.queryForEq(NAME, item.name)

        if (admins.isNotEmpty()) return false

        return adminDao.create(
            AdminMapper.transformItem(item)
        ) > 0
    }

    override fun update(item: AdminItem): Boolean {
        return adminDao.update(
            AdminMapper.transformItem(item)
        ) > 0
    }

    override fun create(item: AccountItem): Boolean {
        return accountDao.create(
            AccountMapper.transformItem(item)
        ) > 0
    }

    override fun update(item: AccountItem): Boolean {
        return accountDao.update(
            AccountMapper.transformItem(item)
        ) > 0
    }

    override fun delete(item: AccountItem): Boolean {
        return adminDao.deleteById(item.id) > 0
    }

    class Builder(val context: IAppContext) {

        fun build(): IAccountManager {
            return AccountManager(this)
        }
    }

    companion object {

        private const val NAME = "name"

        fun create(context: IAppContext, init: AccountManager.Builder.() -> Unit): IAccountManager {
            return AccountManager.Builder(context).apply(init).build()
        }
    }
}