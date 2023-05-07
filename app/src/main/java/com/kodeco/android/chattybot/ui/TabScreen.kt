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
package com.kodeco.chattybot.ui

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.kodeco.android.chattybot.R
import com.kodeco.android.chattybot.ui.ChattingScreen
import com.kodeco.android.chattybot.ui.PersonaScreen

@Composable
fun TabScreen(sharedPreferences: SharedPreferences) {
  var selectedTabIndex by remember { mutableStateOf(0) }

  val tabs = listOf(
    Pair(stringResource(R.string.tab_chat), Icons.Default.Send),
    Pair(stringResource(R.string.tab_persona), Icons.Default.Person),
    Pair(stringResource(R.string.tab_settings), Icons.Default.Settings)
  )

  Column {
    TabRow(selectedTabIndex) {
      tabs.forEachIndexed { index, tab ->
        Tab(
          selected = selectedTabIndex == index,
          onClick = { selectedTabIndex = index },
          icon = {
            Icon(imageVector = tab.second,
              contentDescription = tab.first)
          },
          text = { Text(tab.first) }
        )
      }
    }
    when (selectedTabIndex) {
      0 -> ChattingScreen(sharedPreferences)
      1 -> PersonaScreen(sharedPreferences)
      2 -> SettingsScreen(sharedPreferences)
    }
  }
}