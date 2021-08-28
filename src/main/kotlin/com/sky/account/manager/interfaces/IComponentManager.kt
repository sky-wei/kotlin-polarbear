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

package com.sky.account.manager.interfaces

/**
 * Created by sky on 2021/8/28.
 */
interface IComponentManager {

    /**
     * 获取组件
     * @param name
     * @param <T>
     * @return
    </T> */
    operator fun <T : IComponent> get(name: Class<T>): T

    /**
     * 获取所有组件
     * @return
     */
    fun getAllComponent(): List<IComponent>

    /**
     * 移除组件
     * @param name
     */
    fun remove(name: Class<out IComponent>)

    /**
     * 是否包含组件
     * @param name
     * @return
     */
    operator fun contains(name: Class<out IComponent>): Boolean

    /**
     * 释放所有
     */
    fun release()

    /**
     * 添加监听
     * @param listener
     */
    fun addListener(listener: ComponentListener): Boolean

    /**
     * 移除监听
     * @param listener
     */
    fun removeListener(listener: ComponentListener): Boolean


    /**
     * 工厂类
     */
    interface Factory {

        fun create(context: IAppContext, name: Class<out IComponent>): IComponent
    }


    /**
     * 监听接口类
     */
    interface ComponentListener {

        /**
         * 创建
         * @param name
         */
        fun onCreate(name: Class<out IComponent>)

        /**
         * 释放
         * @param name
         */
        fun onRelease(name: Class<out IComponent>)
    }
}