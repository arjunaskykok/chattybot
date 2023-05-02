package com.kodeco.android.chattybot.model

data class ChatRequest(
  val model: String,
  val messages: List<Message>
)

data class Message(
  val role: String,
  val content: String
)

data class ChatResponse(
  val choices: List<Choice>
)

data class Choice(
  val index: Integer,
  val message: Message
)

