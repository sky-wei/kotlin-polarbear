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
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import com.sky.account.manager.base.AbstractComponent
import com.sky.account.manager.data.disk.entity.AccountEntity
import com.sky.account.manager.data.disk.entity.AdminEntity
import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IDataBaseManager
import com.sky.account.manager.util.FileUtil
import com.sky.account.manager.util.PathUtil
import java.io.File

/**
 * Created by sky on 2021/10/31.
 */
class DataBaseManager(
    builder: Builder
) : AbstractComponent(builder.context), IDataBaseManager {

    private val connectionSource: ConnectionSource
    private val adminDao: Dao<AdminEntity, Int>
    private val accountDao: Dao<AccountEntity, Int>

    init {

        // 创建目录
        FileUtil.mkdirs(builder.dir)

        // 创建连接
        connectionSource = JdbcConnectionSource(
            "jdbc:sqlite:${File(builder.dir, builder.name).path}"
        )

        adminDao = DaoManager.createDao(connectionSource, AdminEntity::class.java)
        accountDao = DaoManager.createDao(connectionSource, AccountEntity::class.java)

        // if you need to create the table
        TableUtils.createTableIfNotExists(connectionSource, AdminEntity::class.java)
        TableUtils.createTableIfNotExists(connectionSource, AccountEntity::class.java)
    }

    override fun release() {
        super.release()
        connectionSource.closeQuietly()
    }

    override fun getAdminDao(): Dao<AdminEntity, Int> {
        return adminDao
    }

    override fun getAccountDao(): Dao<AccountEntity, Int> {
        return accountDao
    }


    class Builder(val context: IAppContext) {

        var dir = "${PathUtil.getUserDir()}/database/"
        var name = "data.db"

        fun build(): IDataBaseManager {
            return DataBaseManager(this)
        }
    }

    companion object {

        fun create(context: IAppContext, init: Builder.() -> Unit): IDataBaseManager {
            return Builder(context).apply(init).build()
        }
    }
}