package com.example.protosuite.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.protosuite.ui.notes.NoteListUI
import com.example.protosuite.ui.notes.NoteViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun MainUI(myViewModel: NoteViewModel, onNavigate: (noteId: Int) -> Unit, onNavigateStart: () -> Unit) {
    val pages = remember { listOf("Notes", "Timer", "Calendar") }
    Column(Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = pages.size)
        val composableScope = rememberCoroutineScope()
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        composableScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) { page ->
            when (page) {
                0 -> {
                    NoteListUI(myViewModel,
                        { noteId: Int ->
                            onNavigate(noteId)
                        },
                        {
                            onNavigateStart()
                        },
                        {})
                }
                1 -> {
                    //TimerUI(myViewModel)
                }
                2 -> {
                    //CalendarUI()
                }
            }
        }
    }
}
/*
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Preview
@Composable
fun TabLayoutPreview() {
    NotesTheme {
        MainUI(navController = rememberNavController())
    }
}
 */

@Composable
fun MainAppBar(myViewModel: NoteViewModel, onDrawerOpen: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Tasky or Plan/r", color = Color.White)
        },
        navigationIcon = {
            IconButton(
                onClick = onDrawerOpen
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                myViewModel.openSortPopup = true
            }) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort",
                    tint = Color.White
                )
            }
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                content = {
                    DropdownMenuItem(onClick = {
                        /* Handle sort! */
                        expanded = false
                    }) {
                        Text("Sort?")
                    }
                    DropdownMenuItem(onClick = {
                        /* Handle settings! */
                        expanded = false
                    }) {
                        Text("Settings")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        /* Handle donate or send feedback! */
                        expanded = false
                    }) {
                        Text("Donate")
                    }
                }
            )
        }
    )
}

@Composable
fun AutoSizingText(modifier: Modifier = Modifier, textStyle: TextStyle = LocalTextStyle.current, text: String) {
    var readyToDraw by remember { mutableStateOf(false) }
    var mutableTextStyle by remember { mutableStateOf(textStyle) }
    Text(
        text = text,
        maxLines = 1,
        softWrap = false,
        overflow = if (readyToDraw) TextOverflow.Ellipsis else TextOverflow.Visible,
        style = mutableTextStyle,
        modifier = modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            if (textLayoutResult.didOverflowWidth) {
                if (mutableTextStyle.fontSize > 16.sp) {
                    mutableTextStyle =
                        mutableTextStyle.copy(fontSize = mutableTextStyle.fontSize * 0.9)
                } else {
                    readyToDraw = true
                }
            } else {
                readyToDraw = true
            }
        },
        textAlign = TextAlign.Center,
        color = Color.Black
    )
}

