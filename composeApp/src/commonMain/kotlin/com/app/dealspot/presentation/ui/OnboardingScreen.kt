package com.app.dealspot.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.dealspot.business.OnboardingPage
import com.app.dealspot.presentation.theme.AppTheme
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.PagerDotColor
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotTextButton
import com.app.dealspot.presentation.utils.Spacer60Height
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.create_and_find_quick_jobs
import dealspot.composeapp.generated.resources.create_and_find_quick_jobs_text
import dealspot.composeapp.generated.resources.earn_or_get_things_faster
import dealspot.composeapp.generated.resources.earn_or_get_things_faster_text
import dealspot.composeapp.generated.resources.ic_create_find_job
import dealspot.composeapp.generated.resources.ic_earn_money
import dealspot.composeapp.generated.resources.ic_safe_fast_local
import dealspot.composeapp.generated.resources.safe_fast_local
import dealspot.composeapp.generated.resources.safe_fast_local_text
import dealspot.composeapp.generated.resources.skip
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onGetStartedClick: () -> Unit = {},
    onSkipClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val onboardingPages = remember {
        listOf(
            OnboardingPage(
                title = Res.string.create_and_find_quick_jobs,
                description = Res.string.create_and_find_quick_jobs_text,
                imageRes = Res.drawable.ic_create_find_job
            ),
            OnboardingPage(
                title = Res.string.earn_or_get_things_faster,
                description =  Res.string.earn_or_get_things_faster_text,
                imageRes = Res.drawable.ic_earn_money
            ),
            OnboardingPage(
                title = Res.string.safe_fast_local,
                description = Res.string.safe_fast_local_text,
                imageRes = Res.drawable.ic_safe_fast_local
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Skip button
        DealSpotTextButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = dimens_20, top = dimens_40),
            onClick = {
                println("OnboardingScreen. Skip button clicked")

                onSkipClick.invoke()
            },
            buttonText = stringResource(Res.string.skip)
        )

        // Main content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimens_60))

            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(
                    page = onboardingPages[page],
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Page indicators
            Row(
                modifier = Modifier.padding(vertical = dimens_30),
                horizontalArrangement = Arrangement.spacedBy(dimens_8)
            ) {
                repeat(onboardingPages.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(dimens_8)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) DealSpotDark else PagerDotColor
                            )
                    )
                }
            }

            // Get Started button
            DealSpotDarkButton(
                modifier = Modifier.padding(horizontal = dimens_20),
                buttonText = if (pagerState.currentPage == onboardingPages.size - 1) "Get Started" else "Next",
                onClick = {
                    val isLastPage = pagerState.currentPage == onboardingPages.size - 1
                    if (isLastPage) {
                        onGetStartedClick()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            )

            Spacer60Height()
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimens_40),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image placeholder (you can replace with actual images)
        Image(
            painter = painterResource(resource = page.imageRes),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(dimens_20))
        )

        Spacer(modifier = Modifier.height(dimens_40))

        // Title
        Text(
            text = stringResource(resource = page.title),
            fontSize = text_size_24,
            fontWeight = FontWeight.W700,
            color = Grey,
            textAlign = TextAlign.Center,
            fontFamily = latoFontFamily(),
            modifier = Modifier.padding(horizontal = dimens_20)
        )

        Spacer(modifier = Modifier.height(dimens_20))

        // Description
        Text(
            text = stringResource(resource = page.description),
            fontSize = text_size_16,
            fontWeight = FontWeight.W500,
            color = grey_700,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            fontFamily = latoFontFamily(),
            modifier = Modifier.padding(horizontal = dimens_10)
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    AppTheme {
        OnboardingScreen()
    }
}
