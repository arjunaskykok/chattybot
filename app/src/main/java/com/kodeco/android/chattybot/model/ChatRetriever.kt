/*
 * Copyright (c) 2023 Kodeco Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.kodeco.android.chattybot.model

import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ChatRetriever(openAIAPIKey: String, persona: String) {
  private val service: OpenAIService
  private val openAIAPIKey: String
  private val persona: String
  private val messages: MutableList<Message>

  companion object {
    const val BASE_URL = "https://api.openai.com/v1/"
  }

  init {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    val retrofit = Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    service = retrofit.create(OpenAIService::class.java)
    this.openAIAPIKey = openAIAPIKey
    this.persona = persona
    if (this.persona.isNotEmpty()) {
      this.messages = mutableListOf(Message(MessageType.SYSTEM.type, persona))
    } else {
      this.messages = mutableListOf()
    }
  }

  fun retrieveChat(callback: Callback<ChatResponse>, message: String) {
    this.messages.add(Message(MessageType.USER.type, message))
    val temperature = 1.0
    val requestBody = ChatRequest("gpt-3.5-turbo", messages, temperature)
    val call = service.getChatCompletions(requestBody, "Bearer $openAIAPIKey")
    call.enqueue(callback)
  }

  fun addReplyMessage(aiMessage: String) {
    this.messages.add(Message(MessageType.ASSISTANT.type, aiMessage))
  }

}