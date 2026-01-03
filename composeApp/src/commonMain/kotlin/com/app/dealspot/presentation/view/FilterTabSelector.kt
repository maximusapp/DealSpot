package com.app.dealspot.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.find_service
import dealspot.composeapp.generated.resources.provide_service_tab
import org.jetbrains.compose.resources.stringResource

@Composable
fun FilterTabSelector(
    selectedType: Int, // 0 = Find Service, 1 = Provide Service
    onTypeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(dimens_40)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(dimens_12)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(dimens_12)
            )
            .padding(2.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(2.dp)
    ) {
        // Find Service tab (type 0)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(dimens_50)
                .background(
                    color = if (selectedType == 0) grey_700 else Color.White,
                    shape = RoundedCornerShape(dimens_12)
                )
                .clickable { onTypeSelected(0) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.find_service),
                fontSize = text_size_16,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily(),
                color = if (selectedType == 0) Color.White else DealSpotDark
            )
        }
        
        // Provide Service tab (type 1)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(dimens_50)
                .background(
                    color = if (selectedType == 1) grey_700 else Color.White,
                    shape = RoundedCornerShape(dimens_12)
                )
                .clickable { onTypeSelected(1) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.provide_service_tab),
                fontSize = text_size_16,
                fontWeight = FontWeight.W600,
                fontFamily = latoFontFamily(),
                color = if (selectedType == 1) Color.White else DealSpotDark
            )
        }
    }
}

