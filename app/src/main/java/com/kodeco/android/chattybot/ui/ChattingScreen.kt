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
package com.kodeco.android.chattybot.ui

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kodeco.android.chattybot.model.*
import com.kodeco.android.chattybot.ui.theme.colorPrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun ChattingScreen(sharedPreferences: SharedPreferences) {
  val openAPIKey = sharedPreferences.getString(OPENAI_API_KEY, "") ?: ""
  var message: String by remember { mutableStateOf("") }
  val historicalMessages = remember { mutableStateListOf<Message>() }
  var addedString by remember { mutableStateOf("") }
  val personaIndex = sharedPreferences.getInt(PERSONA_KEY, 0)
  val persona = Persona.personaInstruction[personaIndex]
  val retriever: ChatRetriever by remember { mutableStateOf(ChatRetriever(openAPIKey, persona)) }
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val callback = object : Callback<ChatResponse> {
    override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
      Log.e("ChattyBot", t.message!!)
      val toastLabel = t.message!!
      coroutineScope.launch {
        Toast.makeText(context, toastLabel, Toast.LENGTH_SHORT).show()
      }
    }

    override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
      response.isSuccessful.let {
        if (!response.body()?.choices.isNullOrEmpty()) {
          val reply = response.body()?.choices!![0].message.content
          historicalMessages.add(Message(MessageType.ASSISTANT, reply))
          retriever.addReplyMessage(reply)
          addedString = reply
        }
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
        itemsIndexed(historicalMessages) { index, messageBox ->
          if (messageBox.role == MessageType.ASSISTANT && index == historicalMessages.size - 1) {
            AIMessage(partText)
          } else if (messageBox.role == MessageType.USER) {
            UserMessage(messageBox.content)
          } else if (messageBox.role == MessageType.ASSISTANT) {
            AIMessage(messageBox.content)
          }
        }
      }
    }
    Box(Modifier.align(Alignment.End)) {
      Row(modifier = Modifier
        .fillMaxWidth()
        .background(colors.onSurface)
        .padding(16.dp)) {
        TextField(
          modifier = Modifier.weight(1f),
          value = message,
          onValueChange = { message = it },
          placeholder = {  },
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colors.background
          )
        )
        Button(
          modifier = Modifier.padding(start = 8.dp),
          onClick = {
            historicalMessages.add(Message(MessageType.USER, message))
            retriever.retrieveChat(callback, message)
            message = ""
          },
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Send,
            contentDescription = null,
            tint = Color.White
          )
        }
      }
    }
  }
  LaunchedEffect(key1 = addedString) {
    addedString.forEachIndexed { charIndex, _ ->
      partText = addedString.substring(startIndex = 0, endIndex = charIndex + 1)
      delay(50)
    }
  }
}
