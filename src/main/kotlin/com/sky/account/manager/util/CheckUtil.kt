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

/**
 * Created by sky on 2021/11/11.
 */
object CheckUtil {

    /**
     * 检验合法
     */
    fun checkRegister(
        name: String,
        password: String,
        confirmPassword: String,
        onError: (message: String) -> Unit
    ): Boolean {

        if (name.isEmpty()) {
            onError("用户名不能为空！")
            return false
        }

        if (password.isEmpty()) {
            onError("密码不能为空！")
            return false
        }

        if (password != confirmPassword) {
            onError("输入的密码不一致！")
            return false
        }
        return true
    }

    /**
     * 检验合法
     */
    fun checkUser(
        name: String,
        password: String,
        onError: (message: String) -> Unit
    ): Boolean {

        if (name.isEmpty()) {
            onError("用户名不能为空！")
            return false
        }

        if (password.isEmpty()) {
            onError("密码不能为空！")
            return false
        }
        return true
    }
}