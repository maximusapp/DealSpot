package com.app.dealspot.presentation.ui.home.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.SpacerHeight12Dp
import com.app.dealspot.presentation.theme.SpacerWidth15Dp
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_16
import com.app.dealspot.presentation.theme.dimens_2
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_200
import com.app.dealspot.presentation.theme.dimens_25
import com.app.dealspot.presentation.theme.dimens_32
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.dimens_6
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.grey_60_transparent
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.view.DealSpotTextButton
import com.app.dealspot.presentation.view.HorizontalThicknessDividerAlpha008
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.looking_for_service
import dealspot.composeapp.generated.resources.looking_for_service_description
import dealspot.composeapp.generated.resources.provide_service
import dealspot.composeapp.generated.resources.provide_service_description
import org.jetbrains.compose.resources.stringResource

private val SheetShape = RoundedCornerShape(dimens_20)

@Composable
fun HomeActionSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onFindServiceClick: () -> Unit,
    onProvideServiceClick: () -> Unit
) {
    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.80f))
                .clickable(
                    indication = null,
                    enabled = false,
                    interactionSource = remember { MutableInteractionSource() }
                ) { /** Clickable disabled */ }
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = dimens_20, vertical = dimens_60)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { /* absorb clicks */ }
            ) {
                Surface(
                    shape = SheetShape,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.shadow(dimens_12, SheetShape)
                ) {
                    Column {
                        SheetItem(
                            icon = Icons.Outlined.Search,
                            title = stringResource(Res.string.looking_for_service),
                            subtitle = stringResource(Res.string.looking_for_service_description),
                            onClick = {
                                println("HomeActionSheet. I search for a service button clicked")
                                onDismissRequest()
                                onFindServiceClick()
                            }
                        )

                        HorizontalThicknessDividerAlpha008()

                        SheetItem(
                            icon = Icons.Outlined.Handshake,
                            title = stringResource(Res.string.provide_service),
                            subtitle = stringResource(Res.string.provide_service_description),
                            onClick = {
                                println("HomeActionSheet. I provide a service button clicked")
                                onDismissRequest()
                                onProvideServiceClick()
                            }
                        )
                    }
                }

                SpacerHeight12Dp()

                Surface(
                    shape = RoundedCornerShape(dimens_16),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onDismissRequest()
                        }
                ) {
                    DealSpotTextButton(
                        modifier = Modifier
                            .width(dimens_200)
                            .height(dimens_45),
                        fontWeight = FontWeight.W600,
                        textColor = DealSpotDark,
                        buttonText = "Cancel",
                        onClick = {
                            println("Create deal. Cancel button clicked")
                            onDismissRequest()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SheetItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = dimens_25, vertical = dimens_20),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
            shape = RoundedCornerShape(dimens_12)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(dimens_32)
                    .padding(dimens_6),
                tint = DealSpotDark
            )
        }

        SpacerWidth15Dp()

        Column(verticalArrangement = Arrangement.spacedBy(dimens_2)) {
            Text(
                text = title,
                color = DealSpotDark,
                fontSize = text_size_16,
                fontFamily = latoFontFamily(),
                fontWeight = FontWeight.W600,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = subtitle,
                fontSize = text_size_14,
                fontFamily = latoFontFamily(),
                fontWeight = FontWeight.W600,
                style = MaterialTheme.typography.labelMedium,
                color = grey_60_transparent
            )
        }
    }
}
