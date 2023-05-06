package com.kodeco.android.chattybot.model

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatRetriever(openAIAPIKey: String, persona: String) {
  private val service: OpenAIService
  private val openAIAPIKey: String
  private val persona: String
  private val messages: MutableList<Message>

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
    this.persona = persona
    if (this.persona.isNotEmpty()) {
      this.messages = mutableListOf(Message("system", persona))
    } else {
      this.messages = mutableListOf()
    }
  }

  fun retrieveChat(callback: Callback<ChatResponse>, message: String) {
    this.messages.add(Message("user", message))
    val requestBody = ChatRequest("gpt-3.5-turbo", messages)
    val call = service.getChatCompletions(requestBody, "Bearer $openAIAPIKey")
    call.enqueue(callback)
  }

  fun addReplyMessage(aiMessage: String) {
    this.messages.add(Message("assistant", aiMessage))
  }

}