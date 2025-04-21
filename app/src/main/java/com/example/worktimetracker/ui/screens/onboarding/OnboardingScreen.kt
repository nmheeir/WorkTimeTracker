import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worktimetracker.ui.navigation.Screens
import com.example.worktimetracker.ui.screens.onboarding.OnboardingUiEvent
import com.example.worktimetracker.ui.screens.onboarding.component.OnBoardingPageData
import com.example.worktimetracker.ui.screens.onboarding.component.PageIndicator
import com.example.worktimetracker.ui.screens.onboarding.component.pages
import com.example.worktimetracker.ui.theme.AppTheme
import com.example.worktimetracker.ui.theme.Typography
import com.example.worktimetracker.ui.theme.WorkTimeTrackerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onNavigateTo: (Screens) -> Unit,
    event: (OnboardingUiEvent) -> Unit
) {
    val pageState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    OnboardingPage(
        items = pages,
        pagerState = pageState,
        onNavigateTo = onNavigateTo,
        event = event
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPage(
    items: List<OnBoardingPageData>,
    pagerState: PagerState,
    onNavigateTo: (Screens) -> Unit,
    event: (OnboardingUiEvent) -> Unit
) {
    val buttonState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> "Next"
                1 -> "Next"
                2 -> "Get Started"
                else -> ""
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            HorizontalPager(state = pagerState) { page ->
                Image(
                    painter = painterResource(id = items[page].image),
                    contentDescription = items[page].title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
        }


        Card(
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .align(Alignment.BottomCenter),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                val coroutineScope = rememberCoroutineScope()
                PageIndicator(
                    pageSize = pages.size,
                    selectedPage = pagerState.currentPage,
                    modifier = Modifier.wrapContentWidth()
                )

                Text(
                    text = items[pagerState.currentPage].title,
                    style = Typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = items[pagerState.currentPage].desc,
                    style = Typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        if (pagerState.currentPage == pages.size - 1) {
                            event(OnboardingUiEvent.SaveAppEntry)
                            onNavigateTo(Screens.AuthNavigator)
                        } else {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.actionSurface, // Màu nền của Button
                        contentColor = AppTheme.colors.onActionSurface  // Màu chữ/icon trên Button
                    )

                ) {
                    Text(text = buttonState.value)
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    WorkTimeTrackerTheme {
        OnboardingScreen(
            onNavigateTo = {},
            event = {}
        )
    }
}