/*
 * Copyright 2025 Charles Zhang
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

package com.google.gemini.cli.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
  name = "GeminiCliSettingsState",
  storages = [Storage("GeminiCliSettings.xml")]
)
class GeminiCliSettingsState : PersistentStateComponent<GeminiCliSettingsState> {
  var cliCommand: String = "gemini"

  override fun getState(): GeminiCliSettingsState {
    return this
  }

  override fun loadState(state: GeminiCliSettingsState) {
    XmlSerializerUtil.copyBean(state, this)
  }

  companion object {
    @JvmStatic
    fun getInstance(): GeminiCliSettingsState {
      val application = ApplicationManager.getApplication()
      if (application.isDisposed) {
        // 如果应用已销毁，返回默认实例
        return GeminiCliSettingsState()
      }

      // 确保服务被正确获取，如果不存在则创建
      return application.getService(GeminiCliSettingsState::class.java)
    }
  }
}
