package com.kodeco.android.chattybot.ui

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
import com.kodeco.android.chattybot.model.Persona

@Composable
fun PersonaScreen() {
  var selectedIndex by remember { mutableStateOf(-1) }

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
            Color.Cyan,
            RoundedCornerShape(if (selectedIndex == index) 16.dp else 0.dp))
          .background(Color.LightGray, RoundedCornerShape(16.dp))
          .clickable {
            selectedIndex = index
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