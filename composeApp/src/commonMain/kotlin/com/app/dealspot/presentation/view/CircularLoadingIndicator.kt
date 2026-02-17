package com.app.dealspot.presentation.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_50
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = DealSpotDark,
    strokeWidth: Dp = 4.dp,
    size: Dp = dimens_40
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val radius = (size.toPx() - strokeWidthPx) / 2f
        val center = androidx.compose.ui.geometry.Offset(
            this.size.width / 2f,
            this.size.height / 2f
        )

        // Background circle
        drawCircle(
            color = color.copy(alpha = 0.2f),
            radius = radius,
            center = center,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )

        // Animated arc
        drawArc(
            color = color,
            startAngle = rotation,
            sweepAngle = 270f, // 3/4 of the circle
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(
                center.x - radius,
                center.y - radius
            ),
            size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun SimpleCircularLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = DealSpotDark,
    size: Dp = dimens_40
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = color,
        strokeWidth = 4.dp
    )
}

@Preview
@Composable
fun CircularLoadingIndicatorPreview() {
    CircularLoadingIndicator()
}

@Preview
@Composable
fun SimpleCircularLoadingIndicatorPreview() {
    SimpleCircularLoadingIndicator()
}
