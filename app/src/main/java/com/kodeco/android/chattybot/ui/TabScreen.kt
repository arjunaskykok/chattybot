package com.kodeco.chattybot.ui

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import com.kodeco.android.chattybot.Greeting

@Composable
fun TabScreen(sharedPreferences: SharedPreferences) {
  var selectedTabIndex by remember { mutableStateOf(0) }

  val tabs = listOf(
    Pair("Chat", Icons.Default.Send),
    Pair("Persona", Icons.Default.Person),
    Pair("Settings", Icons.Default.Settings)
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
      0 -> Greeting("Content for Tab 1")
      1 -> Greeting("Content for Tab 2")
      2 -> SettingsScreen(sharedPreferences)
    }
  }
}