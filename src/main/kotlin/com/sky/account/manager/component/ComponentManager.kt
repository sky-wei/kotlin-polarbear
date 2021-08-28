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

package com.sky.account.manager.component

import com.sky.account.manager.interfaces.IAppContext
import com.sky.account.manager.interfaces.IComponent
import com.sky.account.manager.interfaces.IComponentManager
import java.util.*


/**
 * Created by sky on 2021/8/28.
 */
class ComponentManager(
    builder: Builder
) : AbstractComponent(builder.context), IComponentManager {

    private val mFactory: IComponentManager.Factory = builder.factory

    private val mComponentMap: HashMap<Class<out IComponent>, IComponent?> = HashMap()
    private val mListeners: ArrayList<IComponentManager.ComponentListener> = ArrayList()

    override fun <T : IComponent> get(name: Class<T>): T {
        val component = mComponentMap[name] ?: synchronized(this) {
            mComponentMap[name] ?: mFactory.create(context, name).also {
                it.initialize()
                mComponentMap[name] = it
                callCreateListener(it)
            }
        }
        return name.cast(component)
    }

    override fun getAllComponent(): List<IComponent> {
        return ArrayList<IComponent>(mComponentMap.values)
    }

    override fun remove(name: Class<out IComponent>) {
        mComponentMap[name]?.also {
            it.release()
            callReleaseListener(it)
        }
    }

    override fun contains(
        name: Class<out IComponent>
    ): Boolean = mComponentMap.containsKey(name)

    override fun release() {
        getAllComponent().forEach {
            it.release()
            callReleaseListener(it)
        }
        mComponentMap.clear()
    }

    override fun addListener(
        listener: IComponentManager.ComponentListener
    ): Boolean = mListeners.add(listener)

    override fun removeListener(
        listener: IComponentManager.ComponentListener
    ): Boolean = mListeners.remove(listener)

    /**
     * 通知组件创建
     */
    private fun callCreateListener(component: IComponent) {
        for (listener in mListeners) {
            listener.onCreate(component.javaClass)
        }
    }

    /**
     * 通知组件释放
     */
    private fun callReleaseListener(component: IComponent) {
        for (listener in mListeners) {
            listener.onRelease(component.javaClass)
        }
    }


    /**
     * 内置的工厂
     */
    internal class InternalFactory : IComponentManager.Factory {

        override fun create(context: IAppContext, name: Class<out IComponent>): IComponent {
            return when {
                AbstractComponent::class.java.isAssignableFrom(name) -> {
                    name.getConstructor(IAppContext::class.java)
                        .newInstance(context)
                }
                else -> throw IllegalArgumentException("不支持$name")
            }
        }
    }


    /**
     * Builder
     */
    class Builder(val context: IAppContext) {

        var factory: IComponentManager.Factory = InternalFactory()

        fun build(): IComponentManager {
            return ComponentManager(this)
        }
    }


    companion object {

        /**
         * 创建组件管理
         */
        fun create(context: IAppContext, init: Builder.() -> Unit): IComponentManager {
            return Builder(context).apply(init).build()
        }
    }
}