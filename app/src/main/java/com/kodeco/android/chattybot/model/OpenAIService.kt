package com.kodeco.android.chattybot.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIService {

  @POST("chat/completions")
  @Headers("Content-Type: application/json")
  fun getChatCompletions(@Body request: ChatRequest,
                         @Header("Authorization") authToken: String): Call<ChatResponse>
}