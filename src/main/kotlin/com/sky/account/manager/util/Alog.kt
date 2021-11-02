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
 * Created by sky on 2021/8/28.
 */
class Alog(builder: Builder) {

    companion object {

        /**
         * Priority constant for the println method; use Log.v.
         */
        const val VERBOSE = 2

        /**
         * Priority constant for the println method; use Log.d.
         */
        const val DEBUG = 3

        /**
         * Priority constant for the println method; use Log.i.
         */
        const val INFO = 4

        /**
         * Priority constant for the println method; use Log.w.
         */
        const val WARN = 5

        /**
         * Priority constant for the println method; use Log.e.
         */
        const val ERROR = 6

        /**
         * Priority constant for the println method.
         */
        const val ASSERT = 7


        @Volatile private var INSTANCE: Alog? = null

        /**
         * 获取实例类
         */
        fun getInstance(): Alog =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: create {  }.also { INSTANCE = it }
            }

        /**
         * 设置实例类
         * @param ALog
         */
        fun setSingletonInstance(log: Alog) {
            synchronized(Alog::class.java) {
                check(INSTANCE == null) { "Singleton instance already exists." }
                INSTANCE = log
            }
        }

        fun isDebug(): Boolean {
            return getInstance().mDebug
        }

        fun i(msg: String) {
            getInstance().println(INFO, msg)
        }

        fun i(tag: String, msg: String) {
            getInstance().println(INFO, tag, msg)
        }

        fun i(tag: String, msg: String, tr: Throwable) {
            getInstance().println(INFO, tag, msg, tr)
        }

        fun d(msg: String) {
            getInstance().println(DEBUG, msg)
        }

        fun d(tag: String, msg: String) {
            getInstance().println(DEBUG, tag, msg)
        }

        fun d(tag: String, msg: String, tr: Throwable) {
            getInstance().println(DEBUG, tag, msg, tr)
        }

        fun e(msg: String) {
            getInstance().println(ERROR, msg)
        }

        fun e(msg: String, tr: Throwable) {
            getInstance().println(ERROR, msg, tr)
        }

        fun e(tag: String, msg: String) {
            getInstance().println(ERROR, tag, msg)
        }

        fun e(tag: String, msg: String, tr: Throwable) {
            getInstance().println(ERROR, tag, msg, tr)
        }

        fun v(msg: String) {
            getInstance().println(VERBOSE, msg)
        }

        fun v(tag: String, msg: String) {
            getInstance().println(VERBOSE, tag, msg)
        }

        fun v(tag: String, msg: String, tr: Throwable) {
            getInstance().println(VERBOSE, tag, msg, tr)
        }

        fun w(msg: String) {
            getInstance().println(WARN, msg)
        }

        fun w(tag: String, msg: String) {
            getInstance().println(WARN, tag, msg)
        }

        fun w(tag: String, msg: String, tr: Throwable) {
            getInstance().println(WARN, tag, msg, tr)
        }

        /**
         * 创建对你
         */
        fun create(init: Builder.() -> Unit): Alog {
            return Builder().apply(init).build()
        }
    }


    private val mPrefix: String = builder.prefix
    private val mTag: String = builder.tag
    private val mDebug = builder.debug
    private val mAdapter: Adapter = builder.adapter


    /**
     * 输出日志
     * @param priority
     * @param msg
     */
    fun println(priority: Int, msg: String) {
        if (mDebug) mAdapter.println(priority, mPrefix + mTag, msg)
    }

    /**
     * 输出日志
     * @param priority
     * @param tag
     * @param msg
     */
    fun println(priority: Int, tag: String, msg: String) {
        if (mDebug) mAdapter.println(priority, mPrefix + tag, msg)
    }

    /**
     * 输出日志
     * @param priority
     * @param msg
     * @param tr
     */
    fun println(priority: Int, msg: String, tr: Throwable) {
        if (mDebug) mAdapter.println(priority, mPrefix + mTag, msg, tr)
    }

    /**
     * 输出日志
     * @param priority
     * @param tag
     * @param msg
     * @param tr
     */
    fun println(priority: Int, tag: String, msg: String, tr: Throwable) {
        if (mDebug) mAdapter.println(priority, mPrefix + tag, msg, tr)
    }


    /**
     * 内置适配器
     */
    internal class InternalAdapter : Adapter {

        override fun println(priority: Int, tag: String, msg: String) {
            when(priority) {
                ERROR -> System.err.println("$tag: $msg")
                else -> println("$tag: $msg")
            }
        }

        override fun println(priority: Int, tag: String, msg: String, tr: Throwable) {
            when(priority) {
                ERROR -> {
                    System.err.println("$tag: $msg")
                    tr.printStackTrace()
                }
                else -> println("$tag: $msg")
            }
        }
    }


    class Builder {

        var prefix = "ALog."
        var tag = "Main"
        var debug = false
        var adapter: Adapter = InternalAdapter()

        fun build(): Alog {
            return Alog(this)
        }
    }


    interface Adapter {

        /**
         * 输出日志
         * @param priority
         * @param tag
         * @param msg
         */
        fun println(priority: Int, tag: String, msg: String)

        /**
         * 输出日志
         * @param priority
         * @param tag
         * @param msg
         * @param tr
         */
        fun println(priority: Int, tag: String, msg: String, tr: Throwable)
    }
}