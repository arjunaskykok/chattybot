package com.kodeco.android.chattybot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChattingScreen() {
  var message by remember { mutableStateOf("") }
  val messages = remember { mutableStateListOf<String>() }
  Column(modifier = Modifier.fillMaxSize()) {
    Box(Modifier.weight(1f).align(Alignment.End)) {
      Column(
        modifier = Modifier
          .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
      ) {
        messages.forEach { message ->
          Text(
            text = message)
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
            messages.add(message)
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
}