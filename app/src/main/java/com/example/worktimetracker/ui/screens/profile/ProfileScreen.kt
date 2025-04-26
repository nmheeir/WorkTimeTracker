package com.example.worktimetracker.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.core.presentation.util.AppThemeKey
import com.example.worktimetracker.ui.component.image.CoilImage
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
        profile.takeIf { it != null }?.let { profile ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Header with Image
                item {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Profile Image with Home Button
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    ) {
                        // Profile Image
                        CoilImage(
                            imageUrl = profile.avatarUrl ?: "",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        // Home Button
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2D7CFF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = "Home",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name
                    Text(
                        text = profile.userFullName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Job Title
                    Text(
                        text = profile.designation,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Edit Profile Button
                    Button(
                        onClick = { /* Handle edit profile */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colors.regularSurface,
                            contentColor = AppTheme.colors.onRegularSurface
                        )
                    ) {
                        Text(
                            text = "Edit Profile",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Menu Items
                item {
                    // My Profile
                    MenuItemRow(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "My Profile",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        title = "My Profile"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Settings
                    MenuItemRow(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = "Settings",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        title = "Settings"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Terms & Conditions
                    MenuItemRow(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = "Terms & Conditions",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        title = "Terms & Conditions"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Privacy Policy
                    MenuItemRow(
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = "Privacy Policy",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        title = "Privacy Policy"
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Log Out Button
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.logout()
                                navController.navigate(Screens.LoginScreen.route) {
                                    popUpTo(Screens.ProfileScreen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFEECEC)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Logout,
                                contentDescription = "Log Out",
                                tint = Color(0xFFFF5C5C),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Log out",
                            fontSize = 16.sp,
                            color = Color(0xFFFF5C5C),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }

    }
}

@Composable
fun MenuItemRow(
    icon: @Composable () -> Unit,
    title: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.regularSurface,
            contentColor = AppTheme.colors.onRegularSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = Color.Gray
            )
        }
    }
}