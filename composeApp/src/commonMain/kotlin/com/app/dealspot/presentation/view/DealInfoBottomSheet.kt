package com.app.dealspot.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.app.dealspot.data.model.DealEntity
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_18

private val SheetShape = RoundedCornerShape(topStart = dimens_20, topEnd = dimens_20)

@Composable
fun DealInfoBottomSheet(
    visible: Boolean,
    deal: DealEntity?,
    onDismiss: () -> Unit,
    onSendRequest: () -> Unit,
    onCancel: () -> Unit
) {
    // Dimmed background fades in with delay so it doesn't pop in with the sheet
    var showBackgroundDim by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(180)
            showBackgroundDim = true
        } else {
            showBackgroundDim = false
        }
    }
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (showBackgroundDim) 0.3f else 0f,
        animationSpec = tween(durationMillis = 220)
    )

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
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = backgroundAlpha))
                    .clickable(enabled = true) { onDismiss() }
            )
            Surface(
                shape = SheetShape,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .shadow(dimens_12, SheetShape)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = dimens_20)
                ) {
                    // Top bar: user name and arrow down in one line
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimens_20),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.size(dimens_30))
                        Text(
                            text = deal?.userName ?: "User",
                            fontSize = text_size_18,
                            fontWeight = FontWeight.Bold,
                            fontFamily = latoFontFamily(),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Close",
                            modifier = Modifier
                                .size(dimens_30)
                                .clickable { onDismiss() },
                            tint = Grey
                        )
                    }

                    // serviceName, description
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = deal?.serviceName ?: "",
                            fontSize = text_size_14,
                            fontWeight = FontWeight.W600,
                            fontFamily = latoFontFamily(),
                            color = Grey,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(dimens_12))
                        Text(
                            text = deal?.description ?: "",
                            fontSize = text_size_14,
                            fontWeight = FontWeight.Normal,
                            fontFamily = latoFontFamily(),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Bottom: Cancel (left) and Send Request (right)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = dimens_20),
                        horizontalArrangement = Arrangement.spacedBy(dimens_12),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DealSpotOutlineButton(
                            modifier = Modifier.weight(1f).height(dimens_45),
                            buttonText = "Cancel",
                            enable = true,
                            fillWidth = false,
                            onClick = {
                                onCancel()
                                onDismiss()
                            }
                        )
                        DealSpotDarkButton(
                            modifier = Modifier.weight(1f).height(dimens_45),
                            buttonText = "Send Request",
                            isEnable = true,
                            onClick = {
                                onSendRequest()
                                onDismiss()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(dimens_40))

                }
            }
        }
    }
}
