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

package com.sky.account.manager.data.disk.mapper

import com.sky.account.manager.data.disk.entity.AccountEntity
import com.sky.account.manager.data.model.AccountItem

/**
 * Created by sky on 2021/11/1.
 */
object AccountMapper {

    fun transform(
        entities: List<AccountEntity>
    ): List<AccountItem> {
        return entities.map { transform(it) }
    }

    fun transform(
        entity: AccountEntity
    ): AccountItem {
        return AccountItem(
            entity.id,
            entity.adminId,
            entity.name,
            entity.password,
            entity.url,
            entity.desc,
            entity.createTime
        )
    }

    fun transformItem(
        items: List<AccountItem>
    ): List<AccountEntity> {
        return items.map { transformItem(it) }
    }

    fun transformItem(
        item: AccountItem
    ): AccountEntity {
        return AccountEntity(
            item.id,
            item.adminId,
            item.name,
            item.password,
            item.url,
            item.desc,
            item.createTime
        )
    }
}