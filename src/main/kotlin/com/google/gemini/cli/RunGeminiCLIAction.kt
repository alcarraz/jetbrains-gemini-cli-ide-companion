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

package com.google.gemini.cli

import com.google.gemini.cli.settings.GeminiCliSettingsState
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.wm.ToolWindowManager
import org.jetbrains.plugins.terminal.TerminalToolWindowManager

class RunGeminiCLIAction : AnAction() {
  override fun actionPerformed(e: AnActionEvent) {
    val project = e.project ?: return

    val settingsState = GeminiCliSettingsState.getInstance()
    val cliCommand = settingsState.cliCommand.ifEmpty { "gemini" }
    println("Gemini CLI Action: Using command: '$cliCommand'")

    val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Terminal")
    toolWindow?.show {
      val terminalManager = TerminalToolWindowManager.getInstance(project)
      val widget = terminalManager.createLocalShellWidget(project.basePath, "Gemini CLI")
      widget.executeCommand(cliCommand)
    }
  }
}
