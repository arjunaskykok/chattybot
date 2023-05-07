package com.kodeco.android.chattybot.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UserMessage(content: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End,
    verticalAlignment = Alignment.Bottom
  ) {
    Box(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(0.8f)
        .heightIn(120.dp)
        .background(Color.LightGray, RoundedCornerShape(16.dp))
    ) {
      Text(
        text = content,
        modifier = Modifier
          .align(Alignment.Center)
          .padding(8.dp)
      )
    }
    Icon(
      imageVector = Icons.Default.Person,
      contentDescription = "User Icon"
    )
  }
}