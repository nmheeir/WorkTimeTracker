package com.example.worktimetracker.ui.screens.home.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.worktimetracker.ui.screens.onboarding.component.PageIndicator
import kotlinx.coroutines.delay
import java.lang.Thread.yield
import kotlin.math.absoluteValue


@Preview(showBackground = true)
@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    notification: HomeNotification = listHomeNotification[0]
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF1E88E5),
                                Color(0xFF0D47A1)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Background image
                AsyncImage(
                    model = "",
                    contentDescription = null,
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Text content
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = notification.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = notification.desc,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = notification.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotificationPager(
    modifier: Modifier = Modifier,
    listNotify: List<HomeNotification>
) {
    val notifyPageState = rememberPagerState(initialPage = 0) {
        listNotify.size
    }
    var isUserScrolling by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isUserScrolling) {
        Log.d("userScrolling", isUserScrolling.toString())
        if (!isUserScrolling) {
            while (true) {
                yield()
                delay(3000L)

                if (!isUserScrolling) {
                    val nextPage = (notifyPageState.currentPage + 1) % listNotify.size
                    notifyPageState.animateScrollToPage(nextPage)
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        HorizontalPager(
            state = notifyPageState,
            modifier = Modifier.pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isUserScrolling = true },
                    onDragEnd = { isUserScrolling = false },
                    onDragCancel = { isUserScrolling = false },
                    onDrag = { _, _ ->
                        isUserScrolling = true
                    }
                )
            }
        ) { index ->
            val pageOffset = (
                    (notifyPageState.currentPage - index) + notifyPageState.currentPageOffsetFraction
                    ).absoluteValue

            NotificationCard(
                notification = listNotify[index],
                modifier = Modifier
                    .graphicsLayer {
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            )
        }

        PageIndicator(
            pageSize = listNotify.size,
            selectedPage = notifyPageState.currentPage
        )
    }

}

data class HomeNotification(
    val title: String,
    val desc: String,
    val date: String
)

val listHomeNotification = listOf(
    HomeNotification(
        title = "Payroll Notification",
        desc = "Payroll Notification Description",
        date = "2023-01-01"
    ),
    HomeNotification(
        title = "Payroll Notification",
        desc = "Payroll Notification Description",
        date = "2023-01-01"
    ),
    HomeNotification(
        title = "Payroll Notification",
        desc = "Payroll Notification Description",
        date = "2023-01-01"
    )
)
