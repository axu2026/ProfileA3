package com.example.profilea3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.profilea3.ui.theme.ProfileA3Theme
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfileA3Theme {
                Profile()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {
    // for snackbar confirmation of following
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // states for some partial functionality
    var followers by rememberSaveable { mutableStateOf(345) }
    var following by rememberSaveable { mutableStateOf(123) }
    var followed by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Home",
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "back arrow"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // create a box for the whole screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(innerPadding)
        ) {
            // solid color header box
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(256.dp)
            )
            // profile card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 192.dp, end = 16.dp, bottom = 16.dp)
            ) {
                // line all items in the card with a column
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(all = 16.dp)
                ) {
                    // follow button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        FilledTonalButton(
                            onClick = {
                                followed = !followed
                                var message = "Followed user!"
                                if (followed) {
                                    followers += 1
                                } else {
                                    followers -= 1
                                    message = "Unfollowed user"
                                }
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = message
                                    )
                                }
                            },
                        ) {
                            var label = "Follow"
                            if (followed) label = "Unfollow"
                            Text(text = label)
                        }
                    }
                    // name and info
                    Column () {
                        Text(
                            text = "John Smith",
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "@johnsmith123"
                        )
                    }
                    Text(
                        text = "Hi, my name is John Smith. I live in Boston and I work at a McDonald's."
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.location),
                            contentDescription = "location symbol",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = "Boston, MA")
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(text = "$followers Followers")
                        Text(text = "$following Following")
                    }
                    HorizontalDivider()
                    // extra actions
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(
                            onClick = {},
                            label = { Text("Message") },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Email,
                                    contentDescription = "msg icon"
                                )
                            }
                        )
                        AssistChip(
                            onClick = {},
                            label = { Text("Report") },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Warning,
                                    contentDescription = "report icon"
                                )
                            }
                        )
                        AssistChip(
                            onClick = {},
                            label = { Text("Block") },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "block icon"
                                )
                            }
                        )
                    }
                }
            }
            // circular avatar
            Image(
                painter = painterResource(R.drawable.cat),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(128.dp)
                    .offset(x = 32.dp, y = 128.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileA3Theme {
        Profile()
    }
}