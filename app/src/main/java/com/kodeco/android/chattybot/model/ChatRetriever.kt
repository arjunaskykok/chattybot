package com.kodeco.android.chattybot.model

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatRetriever(openAIAPIKey: String) {
  private val service: OpenAIService
  private val openAIAPIKey: String

  companion object {
    const val BASE_URL = "https://api.openai.com/v1/"
  }

  init {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    service = retrofit.create(OpenAIService::class.java)
    this.openAIAPIKey = openAIAPIKey
  }

  fun retrieveChat(callback: Callback<ChatResponse>, message: String, persona: String) {
    val messages = mutableListOf(
      Message("user", message)
    )
    if (persona.isNotEmpty()) {
      messages.add(0, Message("system", persona))
    }
    val requestBody = ChatRequest("gpt-3.5-turbo", messages)
    val call = service.getChatCompletions(requestBody, "Bearer $openAIAPIKey")
    call.enqueue(callback)
  }
}