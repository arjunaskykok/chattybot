package com.kodeco.chattybot.ui

import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(sharedPreferences: SharedPreferences) {
  var text by remember { mutableStateOf(sharedPreferences.getString("OpenAI API Key", "") ?: "") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Enter your text:",
      style = MaterialTheme.typography.h6
    )
    TextField(
      value = text,
      onValueChange = { text = it },
      placeholder = {
        Text(text = "Type here")
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    )
    Text(
      text = "Forgot your text?",
      style = MaterialTheme.typography.body2,
      color = MaterialTheme.colors.primary,
      modifier = Modifier
        .clickable { /* do something */ }
        .padding(vertical = 8.dp)
    )
    Button(
      onClick = {
        val sharedPrefs = sharedPreferences
        sharedPrefs.edit().putString("OpenAI API Key", text).apply()
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
      Text(text = "Save")
    }
  }
}