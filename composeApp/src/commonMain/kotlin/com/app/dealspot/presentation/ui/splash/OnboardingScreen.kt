package com.app.dealspot.presentation.ui.splash

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.dealspot.presentation.theme.AppTheme
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.PagerDotColor
import com.app.dealspot.presentation.theme.blue
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_56
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.grey_light
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_18
import com.app.dealspot.presentation.theme.text_size_24
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onGetStartedClick: () -> Unit = {},
    onSkipClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val onboardingPages = remember {
        listOf(
            OnboardingPage(
                title = "Find Your Favorite Food",
                description = "Discover the best restaurants and food deals in your area with our smart search and filtering options.",
                imageRes = "ic_food_delivery"
            ),
            OnboardingPage(
                title = "Get Amazing Discounts",
                description = "Save money with exclusive deals and discounts from top restaurants. Never pay full price again!",
                imageRes = "ic_discount"
            ),
            OnboardingPage(
                title = "Order & Enjoy",
                description = "Place your order with just a few taps and enjoy delicious food delivered right to your doorstep.",
                imageRes = "ic_restaurant"
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Skip button
        TextButton(
            onClick = onSkipClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(dimens_20)
        ) {
            Text(
                text = "Skip",
                color = grey_700,
                fontSize = text_size_16,
                fontWeight = FontWeight.W500,
                fontFamily = latoFontFamily()
            )
        }

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
                            .size(if (isSelected) dimens_12 else dimens_8)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) blue else PagerDotColor
                            )
                    )
                }
            }

            // Get Started button
            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens_20)
                    .height(dimens_56),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(dimens_12)
            ) {
                Text(
                    text = if (pagerState.currentPage == onboardingPages.size - 1) "Get Started" else "Next",
                    fontSize = text_size_18,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )
            }

            Spacer(modifier = Modifier.height(dimens_30))
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
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(dimens_20))
                .background(grey_light),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for image - you can add actual images here
            Text(
                text = "🍽️",
                fontSize = 80.sp,
                modifier = Modifier.padding(dimens_20)
            )
        }

        Spacer(modifier = Modifier.height(dimens_40))

        // Title
        Text(
            text = page.title,
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
            text = page.description,
            fontSize = text_size_16,
            fontWeight = FontWeight.W400,
            color = grey_700,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            fontFamily = latoFontFamily(),
            modifier = Modifier.padding(horizontal = dimens_10)
        )
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: String
)

@Preview
@Composable
private fun OnboardingScreenPreview() {
    AppTheme {
        OnboardingScreen()
    }
}
