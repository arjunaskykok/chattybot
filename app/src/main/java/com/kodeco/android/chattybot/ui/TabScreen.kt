package com.kodeco.chattybot.ui

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.kodeco.android.chattybot.Greeting
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