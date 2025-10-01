package com.app.dealspot.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.dimens_90
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.white_30_transparent
import com.app.dealspot.presentation.theme.white_60_transparent
import com.app.dealspot.presentation.theme.white_80_transparent

@Composable
fun SwipedComponent(
    modifier: Modifier = Modifier,
    items: List<String> = listOf()
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) { page ->

            /** Page content */
            Text(
                modifier = Modifier.padding(horizontal = dimens_20).height(dimens_90),
                text = items[page],
                style = MaterialTheme.typography.titleLarge,
                color = white_60_transparent,
                fontWeight = FontWeight.Medium,
                fontFamily = latoFontFamily(),
                textAlign = TextAlign.Center,
                fontSize = text_size_16
            )
        }

        /** Page indicator */
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = dimens_10),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) white_80_transparent else white_30_transparent

                Box(
                    modifier = Modifier
                        .padding(end = dimens_10)
                        .clip(CircleShape)
                        .background(color)
                        .size(dimens_8)
                )
            }
        }
    }
}