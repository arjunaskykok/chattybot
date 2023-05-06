package com.kodeco.android.chattybot.ui

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kodeco.android.chattybot.model.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun ChattingScreen(sharedPreferences: SharedPreferences) {
  val openAPIKey = sharedPreferences.getString(OPENAI_API_KEY, "") ?: ""
  var message by remember { mutableStateOf("") }
  val historicalMessages = remember { mutableStateListOf<Message>() }
  var addedString by remember { mutableStateOf("") }
  val personaIndex = sharedPreferences.getInt(PERSONA_KEY, 0)
  val persona = Persona.personaInstruction[personaIndex]
  val retriever = ChatRetriever(openAPIKey, persona)
  val callback = object : Callback<ChatResponse> {
    override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
      Log.e("ChattingScreen", t.message!!)
    }

    override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
      response.isSuccessful.let {
        val reply = response.body()?.choices!![0].message.content
        historicalMessages.add(Message("assistant", reply))
        retriever.addReplyMessage(reply)
        addedString = reply
      }
    }
  }
  var partText by remember { mutableStateOf("") }
  Column(modifier = Modifier.fillMaxSize()) {
    Box(Modifier.weight(1f).align(Alignment.End)) {
      LazyColumn(
        modifier = Modifier
          .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
      ) {
        itemsIndexed(historicalMessages) { index, message ->
          if (message.role == "assistant" && index == historicalMessages.size - 1) {
            Box(
              modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .heightIn(120.dp)
                .background(Color.DarkGray, RoundedCornerShape(16.dp))
            ) {
              Text(
                text = partText,
                modifier = Modifier
                  .align(Alignment.Center)
                  .padding(8.dp),
                color = Color.White
              )
            }
          } else if (message.role == "user") {
            Box(
              modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .heightIn(120.dp)
                .background(Color.LightGray, RoundedCornerShape(16.dp))
            ) {
              Text(
                text = message.content,
                modifier = Modifier
                  .align(Alignment.Center)
                  .padding(8.dp)
              )
            }
          } else if (message.role == "assistant") {
            Box(
              modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f)
                .heightIn(120.dp)
                .background(Color.DarkGray, RoundedCornerShape(16.dp))
            ) {
              Text(
                text = message.content,
                modifier = Modifier
                  .align(Alignment.Center)
                  .padding(8.dp),
                color = Color.White
              )
            }
          }
        }
      }
    }
    Box(Modifier.align(Alignment.End)) {
      Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(16.dp)) {
        TextField(
          modifier = Modifier.weight(1f),
          value = message,
          onValueChange = { message = it },
          placeholder = {  },
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
          )
        )
        Button(
          modifier = Modifier.padding(start = 8.dp),
          onClick = {
            historicalMessages.add(Message("user", message))
            retriever.retrieveChat(callback, message)
            message = ""
          },
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Send,
            contentDescription = null
          )
        }
      }
    }
  }
  LaunchedEffect(key1 = addedString) {
    addedString.forEachIndexed { charIndex, _ ->
      partText = addedString.substring(startIndex = 0, endIndex = charIndex + 1)
      delay(100)
    }
  }
}