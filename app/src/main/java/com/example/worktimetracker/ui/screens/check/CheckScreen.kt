package com.example.worktimetracker.ui.screens.check

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.example.worktimetracker.R
import com.example.worktimetracker.data.remote.response.Check
import com.example.worktimetracker.domain.result.ApiResult
import com.example.worktimetracker.ui.navigation.Route
import com.example.worktimetracker.ui.screens.home.components.ActivitySection.ActivitySectionItem
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.util.BiometricPromptManager
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun CheckScreen(
    viewModel: CheckViewModel,
    onCheckSuccess: (Route) -> Unit,
    onNavigateTo: (Route) -> Unit,
    promptManager: BiometricPromptManager,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.checkUiEvent.collect {
            when (it) {
                is ApiResult.Success -> {
                    onCheckSuccess(Route.HomeScreen)
                }

                is ApiResult.Error -> {
                    Log.d("CheckScreen", "Lỗi api")
                }

                is ApiResult.NetworkError -> {
                    //nothing
                }
            }
        }
    }

    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity result: $it")
        }
    )

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }

    biometricResult?.let { result ->
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                Log.d("CheckScreen", "Lỗi biometric")
            }

            BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                Log.d("CheckScreen", "Lỗi AuthenticationFailed")
            }

            BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                Log.d("CheckScreen", "Lỗi AuthenticationNotSet")
            }

            is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                Log.d("CheckScreen", "AuthenticationSuccess")
            }

            BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                Log.d("CheckScreen", "Lỗi FeatureUnavailable")
            }

            BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                Log.d("CheckScreen", "Lỗi HardwareUnavailable")
            }
        }
    }

    CheckContent(
        onEvent = viewModel::onEvent,
        onNavigateTo = { onNavigateTo(it) },
        onBack = { onBack() },
        state = viewModel.state,
        promptManager = promptManager
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckContent(
    onEvent: (CheckUiEvent) -> Unit,
    onNavigateTo: (Route) -> Unit,
    state: CheckUiState,
    onBack: () -> Unit,
    promptManager: BiometricPromptManager
) {
    val snackBarHostState = remember { SnackbarHostState() }

    // Request location updates


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Check", style = Typography.labelLarge) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 12.dp, end = 12.dp)
        ) {
            TodayCheck(state = state)
            MapContent(state = state)

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = {
                        promptManager.showBiometricPrompt(
                            title = "Sample prompt",
                            desc = "Sample prompt description"
                        ) {
                            onEvent(CheckUiEvent.CheckIn)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.blue),
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text(text = stringResource(id = R.string.check_in))
                }

                Button(
                    onClick = {
                        promptManager.showBiometricPrompt(
                            title = "Sample prompt",
                            desc = "Sample prompt description"
                        ) {
                            onEvent(CheckUiEvent.CheckOut)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.red),
                        contentColor = colorResource(id = R.color.white)
                    )
                ) {
                    Text(text = stringResource(id = R.string.check_out))
                }
            }
        }
    }
}

@Composable
fun TodayCheck(
    state: CheckUiState
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val columnHeight = screenHeight * 0.3f

    var checkList: List<Check> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(state) {
        checkList = state.todayCheckList
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Cyan)
                    ),
                    shape = RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp) // Hình dạng bo góc nền
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.todaycheck),
                color = Color.White,
                style = Typography.titleMedium // Adjust the style as needed
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(columnHeight)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(columnHeight)
            ) {
                items(checkList.size) {
                    ActivitySectionItem(item = checkList[it])
                }
            }
        }
    }
}


@Composable
fun MapContent(
    state: CheckUiState
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }

    // Request location updates
    LaunchedEffect(fusedLocationClient) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return@LaunchedEffect
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            currentLocation = location
            state.isCurrentStateLoaded = true
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight * 0.3f
    if (!state.isCurrentStateLoaded) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .height(boxHeight)
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading map...", style = Typography.bodyMedium)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .height(boxHeight)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        val mapController = this.controller
                        mapController.setZoom(18.0)
                        this.setMultiTouchControls(true)

                        val startPoint = GeoPoint(10.75507, 106.60345)
                        mapController.setCenter(startPoint)
                        currentLocation?.let { location ->
                            val currentGeoPoint =
                                GeoPoint(location.latitude, location.longitude)
                            val marker = Marker(this)
                            marker.position = currentGeoPoint
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            marker.title = "Current Location"
                            this.overlays.add(marker)
                            mapController.setCenter(currentGeoPoint)
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckContentPreView(

) {
    CheckContent(
        onEvent = {},
        onNavigateTo = {},
        onBack = { Unit },
        state = CheckUiState(isCurrentStateLoaded = false),
        promptManager = BiometricPromptManager(activity = AppCompatActivity())
    )
}

