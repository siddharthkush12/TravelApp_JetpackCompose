package com.example.travelapp.ui.home.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelapp.ui.theme.TealCyan

@Composable
fun MessageScreen(
    receiverId: String,
    onBackClick: () -> Unit,
    viewModel: MessageViewModel = hiltViewModel()
) {

    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var messageText by remember { mutableStateOf("") }

    // Load messages when chat opens
    LaunchedEffect(receiverId) {
        viewModel.loadMessages(receiverId)
        viewModel.connectSocket()
    }

    // Auto scroll when new message arrives
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F1F1))
    ) {

        // -------------------------
        // MESSAGE LIST
        // -------------------------
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            reverseLayout = true
        ) {
            items(
                items = messages,
                key = { message->message.id }
            ) { message ->
                MessageBubble(message)
            }
        }

        // -------------------------
        // INPUT SECTION
        // -------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message") },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.size(6.dp))

            Button(
                onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(receiverId, messageText)
                        println("Send")
                        messageText = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TealCyan
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 15.dp
                )
            ) {
                Text("Send")
            }
        }
    }
}



@Composable
fun MessageBubble(
    message: MessageViewModel.MessageUiModel
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            if (message.isMe) Arrangement.End
            else Arrangement.Start
    ) {

        Column(
            modifier = Modifier
                .background(
                    if (message.isMe) TealCyan else Color.White,
                    RoundedCornerShape(16.dp)
                )
                .padding(10.dp)
                .widthIn(max = 260.dp)
        ) {

            Text(
                text = message.content,
                color = if (message.isMe) Color.White else Color.Black
            )
        }
    }

    Spacer(modifier = Modifier.size(6.dp))
}