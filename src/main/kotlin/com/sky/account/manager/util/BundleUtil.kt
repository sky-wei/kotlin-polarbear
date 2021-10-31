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

package com.sky.account.manager.util

import java.util.*

/**
 * Created by sky on 2021/10/31.
 */
object BundleUtil {

    private const val mBaseName = "bundles.polarbear"

    private val mResBundle: ResourceBundle = try {
        // 加载匹配的属性配置
        ResourceBundle.getBundle(mBaseName)
    } catch (tr: Throwable) {
        // 异常了加载默认的
        ResourceBundle.getBundle(mBaseName, Locale.CHINA)
    }

    fun getResourceBundle(): ResourceBundle {
        return mResBundle
    }

    fun getString(key: String): String {
        return mResBundle.getString(key)
    }
}