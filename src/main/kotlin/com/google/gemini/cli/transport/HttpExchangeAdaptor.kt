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

package com.google.gemini.cli.transport

import com.sun.net.httpserver.HttpExchange
import java.io.InputStream
import java.io.OutputStream

/**
 * An adapter to provide a server-agnostic interface for handling HTTP requests,
 * wrapping the `com.sun.net.httpserver.HttpExchange` object.
 */
class HttpExchangeAdapter(private val exchange: HttpExchange) {

  fun getRequestMethod(): String = exchange.requestMethod

  fun getRequestPath(): String = exchange.requestURI.path

  fun getRequestHeader(name: String): String? = exchange.requestHeaders.getFirst(name)

  fun getRequestBody(): InputStream = exchange.requestBody

  fun setResponseHeader(name: String, value: String) {
    exchange.responseHeaders.add(name, value)
  }

  fun sendResponseHeaders(code: Int, length: Long) {
    exchange.sendResponseHeaders(code, length)
  }

  fun getResponseBody(): OutputStream = exchange.responseBody

  fun close() {
    exchange.close()
  }
}
