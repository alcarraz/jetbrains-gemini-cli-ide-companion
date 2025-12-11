/*
 * Copyright 2025 Charles Zhang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-20.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gemini.cli

import org.junit.Test
import kotlin.test.assertNotNull

class GeminiCliToolWindowContentTest {
    @Test
    fun `test tool window content panel is created`() {
        val toolWindowContent = GeminiCliToolWindowContent()
        assertNotNull(toolWindowContent.contentPanel)
    }
}
