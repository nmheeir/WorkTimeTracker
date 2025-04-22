package com.example.worktimetracker.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttendanceTrackingApp() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar with Profile
            ProfileHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // Date Selector
            DateSelector()

            Spacer(modifier = Modifier.height(24.dp))

            // Today's Attendance Section
            TodayAttendanceSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Your Activity Section
            ActivitySection()

            Spacer(modifier = Modifier.weight(1f))

            // Check In Button
            CheckInButton()

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Bottom Navigation
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Replace with your image
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Name and Title
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Michael Mitc",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Lead UI/UX Designer",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Notification Icon
        IconButton(onClick = { /* Handle notification click */ }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun DateSelector() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DateItem("06", "Thu", false)
        DateItem("07", "Fri", false)
        DateItem("08", "Sat", false)
        DateItem("09", "Sun", true)

        // More icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More dates",
                tint = Color.Black
            )
        }
    }
}


@Composable
fun TodayAttendanceSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Today Attendance",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Check In Card
            AttendanceCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.ArrowForward,
                iconTint = Color(0xFF2D7CFF),
                title = "Check In",
                mainValue = "10:20 am",
                subtitle = "On Time"
            )

            // Check Out Card
            AttendanceCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.ArrowBack,
                iconTint = Color(0xFF2D7CFF),
                title = "Check Out",
                mainValue = "07:00 pm",
                subtitle = "Go Home"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Break Time Card
            AttendanceCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.Coffee,
                iconTint = Color(0xFF2D7CFF),
                title = "Break Time",
                mainValue = "00:30 min",
                subtitle = "Avg Time 30 min"
            )

            // Total Days Card
            AttendanceCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.CalendarMonth,
                iconTint = Color(0xFF2D7CFF),
                title = "Total Days",
                mainValue = "28",
                subtitle = "Working Days"
            )
        }
    }
}

@Composable
fun AttendanceCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    mainValue: String,
    subtitle: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray
            )

            // Main Value
            Text(
                text = mainValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Subtitle
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ActivitySection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Your Activity",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "View All",
                fontSize = 14.sp,
                color = Color(0xFF2D7CFF)
            )
        }

        // Activity Items
        ActivityItem(
            icon = Icons.Outlined.ArrowForward,
            iconTint = Color(0xFF2D7CFF),
            title = "Check In",
            date = "April 17, 2023",
            time = "10:00 am",
            status = "On Time"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActivityItem(
            icon = Icons.Outlined.Coffee,
            iconTint = Color(0xFF2D7CFF),
            title = "Break In",
            date = "April 17, 2023",
            time = "12:30 am",
            status = "On Time"
        )
    }
}

@Composable
fun ActivityItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    title: String,
    date: String,
    time: String,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Activity Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Time and Status
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = time,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = status,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CheckInButton() {
    Button(
        onClick = { /* Handle check in */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2D7CFF)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Check In",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Swipe to Check In",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Home",
                tint = Color(0xFF2D7CFF)
            )

            // Calendar
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "Calendar",
                tint = Color.Gray
            )

            // Center Button
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2D7CFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Main Action",
                    tint = Color.White
                )
            }

            // Team
            Icon(
                imageVector = Icons.Outlined.People,
                contentDescription = "Team",
                tint = Color.Gray
            )

            // Profile
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Profile",
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AttendanceAppPreview() {
    MaterialTheme {
        AttendanceTrackingApp()
    }
}