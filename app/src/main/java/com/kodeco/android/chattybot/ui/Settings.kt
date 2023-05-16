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

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.kodeco.android.chattybot.R
import androidx.compose.ui.unit.dp
import com.kodeco.android.chattybot.model.OPENAI_API_KEY
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(sharedPreferences: SharedPreferences) {
  var text by remember { mutableStateOf(sharedPreferences.getString(OPENAI_API_KEY, "") ?: "") }
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
      color = colors.primaryVariant,
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
        sharedPrefs.edit().putString(OPENAI_API_KEY, text).apply()
        coroutineScope.launch {
            Toast.makeText(context, toastLabel, Toast.LENGTH_SHORT).show()
        }
      },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
      Text(text = stringResource(R.string.save_button),
        color = Color.White)
    }
  }
}