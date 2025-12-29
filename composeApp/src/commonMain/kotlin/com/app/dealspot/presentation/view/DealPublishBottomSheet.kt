package com.app.dealspot.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.dimens_80
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_18
import com.app.dealspot.presentation.theme.text_size_20
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ok
import dealspot.composeapp.generated.resources.success
import dealspot.composeapp.generated.resources.your_deal_published
import dealspot.composeapp.generated.resources.your_request_in_progress
import org.jetbrains.compose.resources.stringResource

private val SheetShape = RoundedCornerShape(topStart = dimens_20, topEnd = dimens_20)

@Composable
fun DealPublishBottomSheet(
    visible: Boolean,
    isLoading: Boolean,
    onOkClicked: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) + slideInVertically(
            animationSpec = tween(300),
            initialOffsetY = { it }
        ),
        exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(
            animationSpec = tween(300),
            targetOffsetY = { it }
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f))
                .clickable(
                    indication = null,
                    enabled = false,
                    interactionSource = remember { MutableInteractionSource() }
                ) { }
        ) {
            Surface(
                shape = SheetShape,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .shadow(dimens_12, SheetShape)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimens_20, vertical = dimens_60),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoading) {
                        // Loading state
                        CircularLoadingIndicator(
                            modifier = Modifier.size(dimens_80),
                            size = dimens_80
                        )
                        
                        SpacerHeight25Dp()
                        
                        Text(
                            text = stringResource(Res.string.your_request_in_progress),
                            fontSize = text_size_18,
                            color = DealSpotDark,
                            fontWeight = FontWeight.W600,
                            fontFamily = latoFontFamily(),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        // Success state with animated icon
                        AnimatedSuccessIcon(
                            modifier = Modifier.size(dimens_80),
                            size = dimens_80,
                            iconSize = dimens_60
                        )
                        
                        SpacerHeight25Dp()
                        
                        Text(
                            text = stringResource(Res.string.success),
                            fontSize = text_size_20,
                            color = DealSpotDark,
                            fontWeight = FontWeight.W700,
                            fontFamily = latoFontFamily(),
                            textAlign = TextAlign.Center
                        )
                        
                        SpacerHeight20Dp()
                        
                        Text(
                            text = stringResource(Res.string.your_deal_published),
                            fontSize = text_size_18,
                            color = Grey,
                            fontWeight = FontWeight.W600,
                            fontFamily = latoFontFamily(),
                            textAlign = TextAlign.Center
                        )
                        
                        SpacerHeight25Dp()
                        
                        DealSpotDarkButton(
                            modifier = Modifier.fillMaxWidth(),
                            buttonText = stringResource(Res.string.ok),
                            isEnable = true,
                            onClick = onOkClicked
                        )
                    }
                }
            }
        }
    }
}

