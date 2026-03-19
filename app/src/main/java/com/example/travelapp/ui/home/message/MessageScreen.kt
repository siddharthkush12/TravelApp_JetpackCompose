package com.example.travelapp.ui.home.message

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelapp.data.remote.dto.message.Message
import com.example.travelapp.di.Session
import com.example.travelapp.ui.theme.TealCyan
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun MessageScreen(
    groupId: String,
    userId: String,
    viewModel: MessageViewModel = hiltViewModel(),
    onClickBack:()->Unit,
    session: Session
) {

    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val text by viewModel.text.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.loadMessages(groupId)
        viewModel.joinGroup(groupId)
        viewModel.listenMessages()
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {

        /* ---------------- TOP BAR ---------------- */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            TealCyan,
                            TealCyan.copy(alpha = 0.7f)
                        )
                    )
                )
                .padding(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable(onClick = onClickBack)
                )

                Spacer(modifier = Modifier.width(22.dp))

                Column {

                    Text(
                        text = "Trip Group Chat",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "${messages.size} messages",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                }
            }
        }

        /* ---------------- MESSAGES ---------------- */

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            items(messages) { message ->

                MessageBubble(
                    message = message,
                    isMine = message.sender._id == userId,
                    session
                )

            }
        }

        /* ---------------- MESSAGE INPUT ---------------- */

        Surface(
            tonalElevation = 5.dp,
            shadowElevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = text,
                    onValueChange = { viewModel.onTextChange(it) },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") },
                    shape = RoundedCornerShape(30.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        viewModel.sendMessage(groupId, userId)
                    },
                    containerColor = TealCyan,
                    modifier = Modifier.size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun MessageBubble(
    message: Message,
    isMine: Boolean,
    session: Session
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement =
            if (isMine) Arrangement.End else Arrangement.Start
    ) {

        if (!isMine) {

            AsyncImage(
                model = message.sender.profilePic,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment =
                if (isMine) Alignment.End else Alignment.Start
        ) {

            if (!isMine) {

                Text(
                    text = message.sender.fullname ?: "",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TealCyan
                )

                Spacer(modifier = Modifier.height(2.dp))
            }

            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isMine) 16.dp else 4.dp,
                    bottomEnd = if (isMine) 4.dp else 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor =
                        if (isMine) TealCyan else Color(0xFFF1F1F1)
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                ) {

                    when (message.type) {

                        "text" -> {

                            Text(
                                text = message.text ?: "",
                                color =
                                    if (isMine) Color.White
                                    else Color.Black,
                                fontSize = 14.sp
                            )

                        }

                        "image" -> {

                            AsyncImage(
                                model = message.mediaUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )

                        }

                        "video" -> {

                            Text(
                                text = "🎥 Video",
                                fontSize = 14.sp
                            )

                        }

                        "file" -> {

                            Text(
                                text = "📎 File",
                                fontSize = 14.sp
                            )

                        }

                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = formatTime(message.createdAt),
                        fontSize = 10.sp,
                        color =
                            if (isMine) Color.White.copy(alpha = 0.7f)
                            else Color.Gray
                    )
                }
            }
        }

        if (isMine) {

            Spacer(modifier = Modifier.width(8.dp))


            AsyncImage(
                model = session.getProfileImage(),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}


fun formatTime(time: String): String {

    return try {

        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )

        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputFormat.parse(time)

        val outputFormat = SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
        )

        outputFormat.format(date!!)

    } catch (e: Exception) {
        ""
    }

}