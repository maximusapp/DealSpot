package com.app.dealspot.presentation.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.dimens_80
import kotlinx.coroutines.delay

@Composable
fun AnimatedSuccessIcon(
    modifier: Modifier = Modifier,
    size: Dp = dimens_80,
    iconSize: Dp = dimens_60,
    backgroundColor: Color = Color(0xFF4CAF50).copy(alpha = 0.15f),
    circleColor: Color = Color(0xFF4CAF50),
    checkmarkColor: Color = Color.White
) {
    val circleProgress = remember { Animatable(0f) }
    val checkmarkProgress = remember { Animatable(0f) }
    val scale = remember { Animatable(0.5f) }
    val bounceScale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        // Start with scale animation (pop in effect)
        scale.animateTo(1f, animationSpec = tween(400, easing = LinearEasing))
        
        // Animate circle drawing (stroke animation)
        circleProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = LinearEasing)
        )
        
        // Small delay before checkmark animation
        delay(150)
        
        // Animate checkmark drawing
        checkmarkProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = LinearEasing)
        )
        
        // Bounce effect when complete
        delay(100)
        bounceScale.animateTo(1.1f, animationSpec = tween(150, easing = LinearEasing))
        bounceScale.animateTo(1f, animationSpec = tween(150, easing = LinearEasing))
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(backgroundColor, shape = CircleShape)
        )
        
        Canvas(
            modifier = Modifier
                .size(iconSize)
                .scale(scale.value * bounceScale.value)
        ) {
            val canvasSize = this.size
            val centerX = canvasSize.width / 2f
            val centerY = canvasSize.height / 2f
            val radius = (canvasSize.minDimension / 2f) * 0.75f
            
            // Draw filled circle background (subtle)
            drawCircle(
                color = backgroundColor,
                radius = radius,
                center = androidx.compose.ui.geometry.Offset(centerX, centerY)
            )
            
            // Draw animated circle stroke (thicker, more prominent)
            drawArc(
                color = circleColor,
                startAngle = -90f,
                sweepAngle = 360f * circleProgress.value,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(
                    centerX - radius,
                    centerY - radius
                ),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                style = Stroke(
                    width = 5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
            
            // Draw animated checkmark (white, thicker)
            if (checkmarkProgress.value > 0f) {
                val startX = centerX - radius * 0.35f
                val startY = centerY
                val midX = centerX - radius * 0.05f
                val midY = centerY + radius * 0.35f
                val endX = centerX + radius * 0.4f
                val endY = centerY - radius * 0.25f
                
                // Calculate total path length
                val firstSegmentLength = kotlin.math.sqrt(
                    (midX - startX) * (midX - startX) + (midY - startY) * (midY - startY)
                )
                val secondSegmentLength = kotlin.math.sqrt(
                    (endX - midX) * (endX - midX) + (endY - midY) * (endY - midY)
                )
                val totalLength = firstSegmentLength + secondSegmentLength
                val animatedLength = totalLength * checkmarkProgress.value
                
                val checkmarkPath = Path().apply {
                    moveTo(startX, startY)
                    
                    if (animatedLength <= firstSegmentLength) {
                        // Draw first segment
                        val progress = animatedLength / firstSegmentLength
                        val currentX = startX + (midX - startX) * progress
                        val currentY = startY + (midY - startY) * progress
                        lineTo(currentX, currentY)
                    } else {
                        // Draw full first segment and partial second segment
                        lineTo(midX, midY)
                        val remainingLength = animatedLength - firstSegmentLength
                        val progress = remainingLength / secondSegmentLength
                        val currentX = midX + (endX - midX) * progress
                        val currentY = midY + (endY - midY) * progress
                        lineTo(currentX, currentY)
                    }
                }
                
                drawPath(
                    path = checkmarkPath,
                    color = checkmarkColor,
                    style = Stroke(
                        width = 6.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = androidx.compose.ui.graphics.StrokeJoin.Round
                    )
                )
            }
        }
    }
}

