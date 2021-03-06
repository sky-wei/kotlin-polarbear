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

package com.sky.account.manager.ex

import com.sky.account.manager.util.BundleUtil

/**
 * Created by sky on 2021/10/31.
 */

fun stringResource(key: String): String {
    return BundleUtil.getString(key)
}

fun stringResource(key: String, vararg args: Any): String {
    return String.format(BundleUtil.getString(key), *args)
}