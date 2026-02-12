package com.app.dealspot.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.dealspot.data.model.DealEntity
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.blueSplashText
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_25
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.dimens_5
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_18
import com.app.dealspot.presentation.utils.serviceIcon
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.cancel
import dealspot.composeapp.generated.resources.cancel_request
import dealspot.composeapp.generated.resources.send_request
import dealspot.composeapp.generated.resources.visit_profile
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val SheetShape = RoundedCornerShape(topStart = dimens_20, topEnd = dimens_20)

@Preview
@Composable
fun DealInfoBottomSheet(
    visible: Boolean,
    deal: DealEntity?,
    currentUserSub: String = "",
    onDismiss: () -> Unit = {},
    onSendRequest: () -> Unit = {},
    onCancel: () -> Unit = {},
    onVisitProfile: () -> Unit = {},
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
//        visible = true,
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

            // Container that holds the card surface and the floating avatar row
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                // Main card surface
                Surface(
                    shape = SheetShape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(dimens_12, SheetShape)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = dimens_20)
                    ) {
                        // Top bar: arrow down in the left corner
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = dimens_20),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Close",
                                modifier = Modifier
                                    .size(dimens_30)
                                    .clickable {
                                        println("DealInfoBottomSheet. Close icon clicked")
                                        onDismiss()
                                    },
                                tint = Grey
                            )

                            if ((currentUserSub != deal?.userSub.orEmpty())) {
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            println("DealInfoBottomSheet. Visit profile text clicked")
                                            onVisitProfile.invoke()
                                        },
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        modifier = Modifier,
                                        text = stringResource(Res.string.visit_profile),
                                        fontSize = text_size_14,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = latoFontFamily(),
                                        color = blueSplashText,
                                        textAlign = TextAlign.End
                                    )

                                    Icon(
                                        imageVector = Icons.Filled.KeyboardDoubleArrowRight,
                                        contentDescription = "profile",
                                        modifier = Modifier.size(dimens_20),
                                        tint = blueSplashText
                                    )
                                }
                            }
                        }

                        // Leave space for the avatar that overlaps the top border
                        Spacer(modifier = Modifier.height(dimens_5))

                        // User name, serviceName, description
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = deal?.userName ?: "User name",
                                fontSize = text_size_18,
                                fontWeight = FontWeight.Bold,
                                fontFamily = latoFontFamily(),
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(dimens_5))

                            Text(
                                text = deal?.serviceName ?: "Specialization name",
                                fontSize = text_size_14,
                                fontWeight = FontWeight.W600,
                                fontFamily = latoFontFamily(),
                                color = grey_middle,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(dimens_12))

                            // Description in a rounded square box with grey border
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1F)
                                    .border(
                                        width = 1.dp,
                                        color = Grey.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(dimens_12)
                                    )
                                    .padding(all = dimens_12)
                            ) {
                                Text(
                                    text = deal?.description ?: "Provided service description.",
                                    fontSize = text_size_14,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = latoFontFamily(),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(dimens_12))

                        // Bottom: Cancel (left) and Send Request (right)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimens_20),
                        ) {
                            DealSpotOutlineButton(
                                modifier = Modifier.fillMaxWidth(),
                                buttonText = stringResource(Res.string.cancel_request),
                                textColor = Color.White,
                                needChangeTextColor = true,
                                borderColor = Color.Red,
                                textSize = text_size_18,
                                enable = true,
                                fillWidth = false,
                                containerColor = Color.Red.copy(alpha = 0.6f),
                                onClick = {
                                    onCancel()
                                    onDismiss()
                                }
                            )

                            DealSpotDarkButton(
                                modifier = Modifier.fillMaxWidth(),
                                buttonText = stringResource(Res.string.send_request),
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

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.54f)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .align(Alignment.TopCenter)
                ) {
                    Image(
                        painter = painterResource(serviceIcon(deal?.categoryId?.toInt() ?: 0)),
                        contentDescription = "User",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(color = Color.White, shape = RoundedCornerShape(50))
                            .height(70.dp).width(70.dp)
                            .padding(15.dp)
                    )
                }
            }

        }
    }
}
