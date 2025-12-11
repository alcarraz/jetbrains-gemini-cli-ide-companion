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

import com.intellij.ide.actions.ShowFilePathAction
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class ShowNoticesAction : AnAction("Show Notices", "Show Gemini CLI notices and licenses", null) {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        try {
            // Try to find the NOTICES.txt file in the plugin directory
            val pluginDir = getPluginDirectory()
            val noticesFile = File(pluginDir, "NOTICES.txt")

            if (noticesFile.exists()) {
                val virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(noticesFile)
                if (virtualFile != null) {
                    FileEditorManager.getInstance(project).openFile(virtualFile, true)
                    return
                }
            }

            // If NOTICES.txt is not found, show an error
            showNotFoundNotification(project)
        } catch (ex: Exception) {
            showErrorNotification(project, ex.message ?: "Unknown error")
        }
    }

    private fun getPluginDirectory(): File {
        // Get the plugin directory
        val pluginClass = this::class.java
        val protectionDomain = pluginClass.protectionDomain
        val codeSource = protectionDomain.codeSource
        val location = codeSource?.location

        return if (location != null) {
            File(location.toURI()).parentFile
        } else {
            // Fallback: try to find it relative to the classpath
            File(".")
        }
    }

    private fun showNotFoundNotification(project: Project) {
        ApplicationManager.getApplication().invokeLater {
            val notification = Notification(
                "Gemini CLI",
                "Notices File Not Found",
                "The NOTICES.txt file could not be found in the plugin directory.",
                NotificationType.WARNING
            )
            notification.notify(project)
        }
    }

    private fun showErrorNotification(project: Project, errorMessage: String) {
        ApplicationManager.getApplication().invokeLater {
            val notification = Notification(
                "Gemini CLI",
                "Error Opening Notices",
                "An error occurred while trying to open the notices file: $errorMessage",
                NotificationType.ERROR
            )
            notification.notify(project)
        }
    }
}
