package com.example.worktimetracker.ui.screens.task

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.worktimetracker.core.ext.format2
import com.example.worktimetracker.ui.component.dialog.DefaultDialog
import com.example.worktimetracker.ui.component.image.CircleImage
import com.example.worktimetracker.ui.component.preferences.PreferenceEntry
import com.example.worktimetracker.ui.viewmodels.TaskDetailUiAction
import com.example.worktimetracker.ui.viewmodels.TaskDetailViewModel
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    navController: NavHostController,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsStateWithLifecycle()
    val isUploading by viewModel.isUploading.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = navController::navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                actions = {
                    var showCreateReportDialog by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { showCreateReportDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.CreateNewFolder,
                            contentDescription = null
                        )
                    }
                    if (showCreateReportDialog) {
                        CreateReportDialog(
                            onDismiss = { showCreateReportDialog = false },
                            isUploading = isUploading,
                            action = viewModel::onAction
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate("task_report/${task?.id}")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        if (task == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No task"
                )
            }
        }
        task.takeIf { it != null }?.let { task ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Main Card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        // Task Header (Title, Status, Date)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Task Title and Status
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = task.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFECE1FF)
                                ) {
                                    Text(
                                        text = task.status.title,
                                        color = Color(0xFF7B3DFF),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    )
                                }
                            }

                            // Creation Date
                            Text(
                                text = "Created at " + task.createdAt.format2(),
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                // Description Section
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Create on boarding page based on pic, pixel perfect, with the user story of i want to know what for i know what is this so i need to view onboarding screen to leverage my knowledge so that i know what kind of apps is this",
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                // Priority and Difficulty Section
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Priority
                            Column {
                                Text(
                                    text = task.priority.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Surface(
                                    modifier = Modifier.padding(top = 8.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFFFECEC)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(Color(0xFFFF3B3B), CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "High",
                                            color = Color(0xFFFF3B3B),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Difficulty
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "Difficulty",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Surface(
                                    modifier = Modifier.padding(top = 8.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFFECFFF5)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        )
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(18.dp)
                                                .background(Color(0xFF00C853), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Check",
                                                tint = Color.White,
                                                modifier = Modifier.size(12.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Very Easy (Less Than a Day)",
                                            color = Color(0xFF00C853),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Assignee Section
                item(
                    key = "assignee_header"
                ) {
                    PreferenceEntry(
                        title = { Text(text = "Assignees") }
                    )
                }

                items(
                    items = task.assignees
                ) { assignee ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Profile Image
                            CircleImage(
                                imageUrl = assignee.avatarUrl,
                                size = 24.dp
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            // Assignee Details
                            Column {
                                Text(
                                    text = assignee.userName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Front End Developer",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                // Comment Section
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Comment Section",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Profile Image
                                Image(
                                    painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                                    contentDescription = "User Profile",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                // Comment Input Field
                                OutlinedTextField(
                                    value = "",
                                    onValueChange = {},
                                    placeholder = {
                                        Text(
                                            "Write a comment...",
                                            color = Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        containerColor = Color(0xFFF5F5F5),
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                    trailingIcon = {
                                        IconButton(onClick = { /* Handle send comment */ }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.Send,
                                                contentDescription = "Send Comment",
                                                tint = Color.Gray
                                            )
                                        }
                                    },
                                    singleLine = true
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
private fun CreateReportDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    isUploading: Boolean,
    action: (TaskDetailUiAction) -> Unit
) {
    val context = LocalContext.current
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileName = getFileName(context, it)
            selectedFile = uriToFile(context, it)
        }
    }

    DefaultDialog(
        onDismiss = onDismiss,
        buttons = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Cancel")
            }
            TextButton(
                enabled = selectedFile != null,
                onClick = { action(TaskDetailUiAction.UploadReportFile(selectedFile!!)) }
            ) {
                Text(text = "Upload")
            }
        }
    ) {
        Box {
            Column {
                val (title, onTitleChange) = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        onTitleChange(it)
                        action(TaskDetailUiAction.ChangeTitle(it))
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                val (description, onDescriptionChange) = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        onDescriptionChange(it)
                        action(TaskDetailUiAction.ChangeDescription(it))
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(onClick = { pdfPickerLauncher.launch("application/pdf") }) {
                    if (selectedFile == null) {
                        Text("Select PDF file")
                    }
                    selectedFileName?.let {
                        Text("Selected File: $it", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            if (isUploading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.3f))
                        .matchParentSize()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

fun getFileName(context: Context, uri: Uri): String {
    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    returnCursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        return it.getString(nameIndex)
    }
    return uri.lastPathSegment ?: "unknown_file"
}

fun uriToFile(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.pdf")
    file.outputStream().use { output -> inputStream?.copyTo(output) }
    return file
}
