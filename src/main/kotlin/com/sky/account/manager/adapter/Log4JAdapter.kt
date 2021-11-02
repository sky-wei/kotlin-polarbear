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

package com.sky.account.manager.adapter

import com.sky.account.manager.util.Alog
import com.sky.account.manager.util.ResUtil
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.Priority
import org.apache.log4j.PropertyConfigurator

/**
 * Created by sky on 2021/8/28.
 */
class Log4JAdapter : Alog.Adapter {

    companion object {

        private val LOG = Logger.getLogger("polarBear")

        init {
            PropertyConfigurator.configure(
                ResUtil.getResource("log4j.properties")
            )
        }
    }

    override fun println(priority: Int, tag: String, msg: String) {
        LOG.log(transform(priority), "$tag $msg")
    }

    override fun println(priority: Int, tag: String, msg: String, tr: Throwable) {
        LOG.log(transform(priority), "$tag $msg", tr)
    }

    private fun transform(priority: Int): Priority {
        return when(priority) {
            Alog.VERBOSE -> Level.ALL
            Alog.DEBUG -> Level.DEBUG
            Alog.INFO -> Level.INFO
            Alog.WARN -> Level.WARN
            Alog.ERROR -> Level.ERROR
            else -> Level.ALL
        }
    }
}