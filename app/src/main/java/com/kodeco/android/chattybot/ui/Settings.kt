package com.kodeco.chattybot.ui

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kodeco.android.chattybot.R
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(sharedPreferences: SharedPreferences) {
  var text by remember { mutableStateOf(sharedPreferences.getString("OpenAI API Key", "") ?: "") }
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val toastLabel = stringResource(R.string.toast_label)
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = stringResource(R.string.enter_api_key),
      style = MaterialTheme.typography.h6
    )
    TextField(
      value = text,
      onValueChange = { text = it },
      placeholder = {
        Text(text = stringResource(R.string.api_key_placeholder))
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    )
    Text(
      text = stringResource(R.string.openai_api_key_link),
      style = MaterialTheme.typography.body2,
      color = MaterialTheme.colors.primary,
      modifier = Modifier
        .clickable {
          val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://platform.openai.com/account/api-keys"))
          context.startActivity(intent)
        }
        .padding(vertical = 8.dp)
    )
    Button(
      onClick = {
        val sharedPrefs = sharedPreferences
        sharedPrefs.edit().putString("OpenAI API Key", text).apply()
        coroutineScope.launch {
            Toast.makeText(context, toastLabel, Toast.LENGTH_SHORT).show()
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
      Text(text = stringResource(R.string.save_button))
    }
  }
}