package com.kodeco.android.chattybot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PersonaScreen() {
  val headers = listOf("English Translator and Improver",
    "Android Developer Interviewer",
    "Travel Guide in Tokyo",
    "Motivational Coach"
  )
  val dummyTextList = listOf(
    "I want you to act as an English translator and improver. " +
        "I'll speak to you in any languages and you translate it " +
        "to English. If I speak English to you, then you'll " +
        "fix the grammar and make it more natural.",
    "I want you to act as an interviewer. I will be a candidate " +
        "for the Android developer position. Then you'll reply as " +
        "the person who's doing the technical interview.",
    "I want you to act as a travel guide in Tokyo. I'll post questions as an traveler " +
        "then you'll give guide for me to enjoy Tokyo.",
    "I want you to act as a motivational coach that help me improve " +
        "my self-esteem and confidence. " +
        "Help me be a better person.")
  LazyColumn {
    items(dummyTextList.size) { index ->
      Text(
        text = headers[index],
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
          .background(Color.LightGray, RoundedCornerShape(16.dp))
      ) {
        Text(
          text = dummyTextList[index],
          modifier = Modifier
            .align(Alignment.Center)
            .padding(8.dp)
        )
      }
    }
  }
}