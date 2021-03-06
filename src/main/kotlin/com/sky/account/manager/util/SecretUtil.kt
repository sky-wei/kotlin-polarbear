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

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by sky on 2021-11-05.
 */
object SecretUtil {

    private const val HASH_ALGORITHM = "SHA-256"
    private val ivBytes = byteArrayOf(98, 10, 60, 80, 20, 1, 9, 8, 9, 20, 21, 11, 10, 26, 6, 97)

    fun buildKey(password: String): SecretKey {
        val key = MessageDigest.getInstance(HASH_ALGORITHM).run {
            val bytes = password.toByteArray()
            update(bytes, 0, bytes.size)
            digest()
        }
        return SecretKeySpec(key, "AES")
    }

    fun encrypt(key: SecretKey, value: ByteArray): ByteArray {
        return Cipher.getInstance("AES/CBC/PKCS5Padding")
            .run {
                init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(ivBytes))
                doFinal(value)
            }
    }

    fun decrypt(key: SecretKey, value: ByteArray): ByteArray {
        return Cipher.getInstance("AES/CBC/PKCS5Padding")
            .run {
                init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ivBytes))
                doFinal(value)
            }
    }

    fun encrypt(password: String, value: String): String {
        return encrypt(buildKey(password), value.toByteArray())
            .run { MD5Util.bytesToHexString(this) }
    }

    fun decrypt(password: String, value: String): String {
        return decrypt(buildKey(password), MD5Util.hexStringToBytes(value))
            .run { String(this) }
    }
}