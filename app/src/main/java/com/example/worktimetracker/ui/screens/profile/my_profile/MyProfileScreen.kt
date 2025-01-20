package com.example.worktimetracker.ui.screens.profile.my_profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.User
import com.example.worktimetracker.ui.component.background.LinearBackground
import com.example.worktimetracker.ui.screens.profile.component.MyProfileItem
import com.example.worktimetracker.ui.screens.profile.component.MyProfileListItem
import com.example.worktimetracker.ui.screens.profile.component.MyProfileOptionButton
import com.example.worktimetracker.ui.screens.profile.component.OptionTopBar
import com.example.worktimetracker.ui.screens.profile.component.lOptionMyProfile
import com.example.worktimetracker.ui.screens.sharedViewModel.SharedUiState
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier,
    state: SharedUiState = SharedUiState(),
    onBack: () -> Unit = {}
) {

    var user: User
    var personalList: List<MyProfileItem> by remember {
        mutableStateOf(emptyList())
    }
    var professionalList: List<MyProfileItem> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(state) {
        user = state.user
        // TODO: tạm thời chia thông tin user bằng cách này
        personalList = listOf(
            MyProfileItem("Full name", user.userFullName),
            MyProfileItem("Email Address", user.email),
            MyProfileItem("Phone number", user.phoneNumber),
            MyProfileItem("Address", user.address),
        )
        professionalList = listOf(
            MyProfileItem("Employee ID", user.id.toString()),
            MyProfileItem("Designation", user.designation),
            MyProfileItem("Employee Type", user.employeeType.toString()),
            MyProfileItem("Department", user.department),
            MyProfileItem("Phone number", user.phoneNumber)
        )
        Log.d("MyProfileScreenLaunchedEffect", user.toString())
    }
WorkTimeTrackerTheme {
    LinearBackground {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.my_profile), style = MaterialTheme.typography.titleLarge, color = AppTheme.colors.onBackground) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = AppTheme.colors.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) {
        var selectedItem by remember {
            mutableStateOf(lOptionMyProfile[0])
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp
                )
                .fillMaxSize()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(lOptionMyProfile) { index ->
                    MyProfileOptionButton(
                        isClick = selectedItem == index,
                        title = index,
                        onClick = {
                            selectedItem = index
                        }
                    )
                }
            }
            when (selectedItem) {
                lOptionMyProfile[0] -> {
                    MyProfileListItem(list = personalList)
                }

                lOptionMyProfile[1] -> {
                    MyProfileListItem(list = professionalList)
                }

                lOptionMyProfile[2] -> {
                    // TODO: thêm file tài liệu vào trong này
                }
            }
        }
    }
}}}