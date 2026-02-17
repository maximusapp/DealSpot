package com.app.dealspot.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.dealspot.domain.model.ServiceCategoryEntity
import com.app.dealspot.domain.model.ServiceEntity
import com.app.dealspot.presentation.ui.home.search_provide_for_service.selection.ServiceSelectionSheet
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.dimens_12
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_30
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.apply_filter
import dealspot.composeapp.generated.resources.close
import dealspot.composeapp.generated.resources.ic_close
import dealspot.composeapp.generated.resources.type_of_service
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FilterBottomSheet(
    visible: Boolean,
    selectedFilterType: Int, // 0 = Find Service, 1 = Provide Service
    onFilterTypeChanged: (Int) -> Unit,
    onClose: () -> Unit,
    onApplyFilter: (
        selectedCategory: ServiceCategoryEntity?,
        selectedService: ServiceEntity?
    ) -> Unit = { _, _ -> },
    onClearFilter: () -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf<ServiceCategoryEntity?>(null) }
    var selectedService by remember { mutableStateOf<ServiceEntity?>(null) }

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
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Close icon in top right corner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimens_20, end = dimens_20),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = "Close filter",
                        modifier = Modifier
                            .size(dimens_30)
                            .clickable {
                                println("FilterBottomSheet. Close filter clicked")
                                onClose()
                            },
                        tint = Grey
                    )
                }
                
                // Tab selector at the top
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimens_20)
                        .padding(top = dimens_20)
                ) {
                    Text(
                        text = stringResource(Res.string.type_of_service),
                        fontSize = text_size_14,
                        fontWeight = FontWeight.W600,
                        fontFamily = latoFontFamily(),
                        color = grey_700,
                        modifier = Modifier.padding(bottom = dimens_12)
                    )
                    FilterTabSelector(
                        selectedType = selectedFilterType,
                        onTypeSelected = onFilterTypeChanged
                    )
                }
                
                // Service selection content
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    ServiceSelectionSheet(
                        visible = visible,
                        selectedCategory = selectedCategory,
                        selectedService = selectedService,
                        onDismissRequest = { /* No-op, handled by filter close */ },
                        onServiceSelected = { category, service ->
                            selectedCategory = category
                            selectedService = service
                        },
                        showCancelButton = false,
                        showBackground = false,
                        horizontalPadding = 10.dp,
                        verticalPadding = 15.dp,
                        needSetSheetMaxHeight = false
                    )
                }
                
                // Buttons at the bottom
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimens_20)
                        .padding(top = dimens_20)
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(bottom = dimens_20),
                    horizontalArrangement = Arrangement.spacedBy(dimens_12)
                ) {
                    DealSpotDarkButton(
                        modifier = Modifier.weight(1f).height(dimens_45),
                        buttonText = stringResource(Res.string.apply_filter),
                        isEnable = true,
                        onClick = {
                            // Pass the currently selected category and service
                            println("FilterBottomSheet. Apply filter clicked")
                            onApplyFilter(selectedCategory, selectedService)
                            onClose()
                        }
                    )
                    
                    DealSpotOutlineButton(
                        modifier = Modifier.weight(1f).height(dimens_45),
                        buttonText = stringResource(Res.string.close),
                        enable = true,
                        fillWidth = false,
                        onClick = {
                            println("FilterBottomSheet. Clear filter clicked")
                            // Reset local selection state so ServiceSelectionSheet is cleared too
                            selectedCategory = null
                            selectedService = null
                            onClearFilter()
                            onClose()
                        }
                    )
                }
            }
        }
    }
}

