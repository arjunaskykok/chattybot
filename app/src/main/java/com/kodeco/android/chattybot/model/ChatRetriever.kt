package com.kodeco.android.chattybot.model

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatRetriever {
  private val service: OpenAIService

  companion object {
    const val BASE_URL = "https://api.openai.com/v1/"
  }

  init {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    service = retrofit.create(OpenAIService::class.java)
  }

  fun retrieveChat(callback: Callback<ChatResponse>) {
    val requestBody = ChatRequest("gpt-3.5-turbo",
      listOf(Message("user", "Hello")))
    val openaiKey = ""
    val call = service.getChatCompletions(requestBody, "Bearer $openaiKey")
    call.enqueue(callback)
  }
}