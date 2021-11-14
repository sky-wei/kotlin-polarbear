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

package com.sky.account.manager.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

/**
 * Created by sky on 2021-11-12.
 */
@Composable
fun FileDialog(
    title: String,
    isLoad: Boolean = true,
    onFilter: (dir: File, name: String) -> Boolean = { _, _ -> true },
    onResult: (result: File?) -> Unit
) = AwtWindow(
    create = {
        val owner: Frame? = null
        object : FileDialog(owner, "Choose a file", if (isLoad) LOAD else SAVE) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    if (file != null) {
                        onResult(File(directory).resolve(file))
                    } else {
                        onResult(null)
                    }
                }
            }
        }.apply {
            this.title = title
            this.isMultipleMode = false
            this.setFilenameFilter { dir, name -> onFilter(dir, name) }
        }
    },
    dispose = FileDialog::dispose
)