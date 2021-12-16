package com.augustbyrne.tas.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.augustbyrne.tas.R
import com.augustbyrne.tas.ui.notes.DarkMode
import com.augustbyrne.tas.ui.notes.EditOneFieldDialog
import com.augustbyrne.tas.ui.notes.NoteViewModel
import com.augustbyrne.tas.ui.values.blue500
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsUI(myViewModel: NoteViewModel, onNavBack: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val localCoroutineScope = rememberCoroutineScope()
    var showAdHiderPopup by rememberSaveable { mutableStateOf(false) }
    var showDarkModeDialog by rememberSaveable { mutableStateOf(false) }
    val darkModeState by myViewModel.isDarkThemeFlow.observeAsState(initial = DarkMode.System)
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Settings")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                text = "Theme",
                style = MaterialTheme.typography.titleSmall,
                color = blue500
            )
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            showDarkModeDialog = true
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Dark mode",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = when (darkModeState) {
                        DarkMode.System -> "Follow system"
                        DarkMode.Off -> "Off"
                        DarkMode.On -> "On"
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
/*            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        onClick = {
                            showColorPopup = true
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    )
                    .padding(16.dp),
                text = "Timer Background",
                style = MaterialTheme.typography.bodyLarge
            )*/
            Divider(modifier = Modifier.fillMaxWidth())
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                text = "Other",
                style = MaterialTheme.typography.titleSmall,
                color = blue500
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        onClick = {
                            showAdHiderPopup = true
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    )
                    .padding(16.dp),
                text = "Remove ads",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        onClick = {
                            uriHandler.openUri("https://github.com/august-byrne/ProtoSuite")
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    )
                    .padding(16.dp),
                text = "Project github",
                style = MaterialTheme.typography.bodyLarge
            )
            if (showAdHiderPopup) {
                EditOneFieldDialog(
                    headerName = "Speak friend & enter",
                    fieldName = "Password",
                    initialValue = "",
                    inputType = KeyboardType.Password,
                    onDismissRequest = { showAdHiderPopup = false },
                    onAccepted = { userInput ->
                        localCoroutineScope.launch {
                            myViewModel.setShowAds(
                                userInput != context.getString(R.string.no_ads_password)
                            )
                            showAdHiderPopup = false
                        }
                    }
                )
            }
            if (showDarkModeDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDarkModeDialog = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    ),
                    title = {
                        Text("Dark mode")
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(CornerSize(30.dp)))
                                    .clickable(
                                        onClick = {
                                            showDarkModeDialog = false
                                            localCoroutineScope.launch {
                                                myViewModel.setIsDarkTheme(DarkMode.System)
                                            }
                                        },
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple()
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = darkModeState == DarkMode.System,
                                    onClick = {
                                        showDarkModeDialog = false
                                        localCoroutineScope.launch {
                                            myViewModel.setIsDarkTheme(DarkMode.System)
                                        }
                                    }
                                )
                                Spacer(Modifier.width(16.dp))
                                Text(text = "Follow system", style = MaterialTheme.typography.bodyLarge)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(CornerSize(30.dp)))
                                    .clickable(
                                        onClick = {
                                            showDarkModeDialog = false
                                            localCoroutineScope.launch {
                                                myViewModel.setIsDarkTheme(DarkMode.Off)
                                            }
                                        },
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple()
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = darkModeState == DarkMode.Off,
                                    onClick = {
                                        showDarkModeDialog = false
                                        localCoroutineScope.launch {
                                            myViewModel.setIsDarkTheme(DarkMode.Off)
                                        }
                                    }
                                )
                                Spacer(Modifier.width(16.dp))
                                Text(text = "Off", style = MaterialTheme.typography.bodyLarge)
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(CornerSize(30.dp)))
                                    .clickable(
                                        onClick = {
                                            showDarkModeDialog = false
                                            localCoroutineScope.launch {
                                                myViewModel.setIsDarkTheme(DarkMode.On)
                                            }
                                        },
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple()
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = darkModeState == DarkMode.On,
                                    onClick = {
                                        showDarkModeDialog = false
                                        localCoroutineScope.launch {
                                            myViewModel.setIsDarkTheme(DarkMode.On)
                                        }
                                    }
                                )
                                Spacer(Modifier.width(16.dp))
                                Text(text = "On", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { showDarkModeDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
