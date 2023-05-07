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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kodeco.android.chattybot.model.PERSONA_KEY
import com.kodeco.android.chattybot.model.Persona

@Composable
fun PersonaScreen(sharedPreferences: SharedPreferences) {
  var selectedIndex by remember { mutableStateOf(sharedPreferences.getInt(PERSONA_KEY, -1)) }
  LazyColumn {
    items(Persona.personaInstruction.size) { index ->
      Text(
        text = Persona.headers[index],
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 20.dp, top = 20.dp)
      )
      Box(
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
          .height(120.dp)
          .border(if (selectedIndex == index) 4.dp else 0.dp,
            if (selectedIndex == index) Color.Cyan else Color.White,
            RoundedCornerShape(if (selectedIndex == index) 16.dp else 0.dp))
          .background(Color.LightGray, RoundedCornerShape(16.dp))
          .clickable {
            selectedIndex = index
            val sharedPrefs = sharedPreferences
            sharedPrefs.edit().putInt(PERSONA_KEY, index).apply()
          }
      ) {
        Text(
          text = Persona.personaInstruction[index],
          modifier = Modifier
            .align(Alignment.Center)
            .padding(8.dp)
        )
      }
    }
  }
}