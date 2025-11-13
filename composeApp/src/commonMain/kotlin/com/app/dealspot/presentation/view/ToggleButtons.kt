package com.app.dealspot.presentation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.urgent_problem
import org.jetbrains.compose.resources.stringResource

@Composable
fun ToggleWithLeftText(
    modifier: Modifier,
    toggleText: String = "",
    onToggleActive: (Boolean) -> Unit = {}
) {
    var isActive by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.urgent_problem),
            fontSize = text_size_16,
            color = Grey,
            fontWeight = FontWeight.W600,
            fontFamily = latoFontFamily(),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isActive,
            onCheckedChange = {
                isActive = it
                onToggleActive.invoke(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = DealSpotDark,
                checkedTrackColor = DealSpotDark.copy(alpha = 0.5f),
                uncheckedThumbColor = grey_middle,
                uncheckedTrackColor = grey_middle.copy(alpha = 0.5f)
            )
        )
    }
}