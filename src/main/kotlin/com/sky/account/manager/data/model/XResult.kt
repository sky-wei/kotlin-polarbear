package com.sky.account.manager.data.model

/**
 * Created by sky on 2021-11-03.
 */
sealed class XResult<out T> {

    data class Success<out T>(val value: T) : XResult<T>()

    data class Failure(val throwable: Throwable) : XResult<Nothing>()

    companion object {

        val Invalid = Failure(NullPointerException())
    }
}