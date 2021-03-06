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

import com.sky.account.manager.data.model.XResult
import com.sky.account.manager.util.Alog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

/**
 * Created by sky on 2021-11-03.
 */
inline fun <reified T> XResult<T>.doSuccess(success: (T) -> Unit): XResult<T> {
    if (this is XResult.Success) {
        success(value)
    }
    return this
}

inline fun <reified T> XResult<T>.doFailure(failure: (Throwable) -> Unit): XResult<T> {
    if (this is XResult.Failure) {
        Alog.e("处理异常", throwable)
        failure(throwable)
    }
    return this
}

suspend fun <T> runOfResult(block: suspend () -> T): XResult<T> {
    return try {
        withContext(Dispatchers.IO) {
            XResult.Success(block())
        }
    } catch (tr: Throwable) {
        XResult.Failure(tr)
    }
}

fun <T> flowOfResult(block: suspend () -> T): Flow<XResult<T>> = flow {
    try {
        emit(
            withContext(Dispatchers.IO) {
                XResult.Success(block.invoke())
            }
        )
    } catch (tr: Throwable) {
        emit(XResult.Failure(tr))
    }
}

fun <T> flowOfResultNull(block: suspend () -> T?): Flow<XResult<T>> = flow {
    try {
        block.invoke()?.also { emit(XResult.Success(it)) } ?: emit(XResult.Invalid)
    } catch (tr: Throwable) {
        emit(XResult.Failure(tr))
    }
}

fun <T> XResult<T>.asFlow(): Flow<XResult<T>> = flowOf(this)

fun <T> concatResult(
    source1: Flow<XResult<T>>,
    source2: () -> Flow<XResult<T>>
): Flow<XResult<T>> {
    return source1
        .flatMapConcat {
            if (it is XResult.Failure) {
                source2.invoke()
            } else {
                it.asFlow()
            }
        }
}