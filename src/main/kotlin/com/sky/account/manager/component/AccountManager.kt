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
import com.sky.account.manager.data.DataException
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

    override fun create(item: AdminItem): AdminItem {

        val admins = adminDao.queryForEq(NAME, item.name)

        if (admins.isNotEmpty()) {
            // 已经存在不需要处理
            throw DataException("账号${item.name}已经存在不能重复创建!")
        }

        val id = adminDao.create(
            AdminMapper.transformItem(item)
        )
        return item.copy(id = id)
    }

    override fun login(item: AdminItem): AdminItem {

        val admins = adminDao.queryForEq(NAME, item.name)

        if (admins.isEmpty()) {
            // 用户不存在
            throw DataException("输入的用户名或密码错误!")
        }

        val admin = admins[0]

        if (admin.password != item.password) {
            // 密码错误
            throw DataException("输入的用户名或密码错误!")
        }

        return AdminMapper.transform(admin)
    }

    override fun load(adminId: Int): List<AccountItem> {
        return accountDao.queryBuilder()
            .orderBy(CREATE_TIME, false)
            .where()
            .eq(ADMIN_ID, adminId)
            .query()
            .map { AccountMapper.transform(it) }
    }

    override fun search(adminId: Int, keyword: String): List<AccountItem> {
        return accountDao.queryBuilder()
            .orderBy(CREATE_TIME, false)
            .where()
            .eq(ADMIN_ID, adminId)
            .and()
            .like(DESC, "%$keyword%")
            .and()
            .like(URL, "%$keyword%")
            .query()
            .map { AccountMapper.transform(it) }

    }

    override fun clearData(adminId: Int): Boolean {
        return accountDao.queryBuilder()
            .where()
            .eq(ADMIN_ID, adminId)
            .query()
            .run { accountDao.delete(this) > 0 }
    }

    override fun update(item: AdminItem): AdminItem {
        adminDao.update(AdminMapper.transformItem(item))
        return item
    }

    override fun create(item: AccountItem): AccountItem {
        val id = accountDao.create(
            AccountMapper.transformItem(item)
        )
        return item.copy(id = id)
    }

    override fun create(items: List<AccountItem>): List<AccountItem> {
        accountDao.create(AccountMapper.transformItem(items))
        return items
    }

    override fun update(item: AccountItem): AccountItem {
        accountDao.update(AccountMapper.transformItem(item))
        return item
    }

    override fun update(item: AdminItem, items: List<AccountItem>): List<AccountItem> {

        val admins = adminDao.queryForEq(NAME, item.name)

        if (admins.isEmpty()) {
            // 用户不存在
            throw DataException("修改用户名信息错误!")
        }

        update(item)
        items.forEach { update(it) }
        return items
    }

    override fun delete(item: AccountItem): AccountItem {
        accountDao.deleteById(item.id)
        return item
    }

    class Builder(val context: IAppContext) {

        fun build(): IAccountManager {
            return AccountManager(this)
        }
    }

    companion object {

        private const val NAME = "name"

        private const val ADMIN_ID = "adminId"

        private const val DESC = "desc"

        private const val URL = "url"

        private const val CREATE_TIME = "createTime"

        fun create(context: IAppContext, init: Builder.() -> Unit): IAccountManager {
            return Builder(context).apply(init).build()
        }
    }
}