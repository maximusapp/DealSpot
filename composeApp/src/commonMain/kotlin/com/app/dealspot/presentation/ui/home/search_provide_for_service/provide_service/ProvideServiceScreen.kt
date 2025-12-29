package com.app.dealspot.presentation.ui.home.search_provide_for_service.provide_service

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.app.dealspot.business.ScreenType
import com.app.dealspot.data.model.MapCameraState
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight5Dp
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_100
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_300
import com.app.dealspot.presentation.theme.dimens_5
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.grey_700_70_transparent
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.ui.components.SelectableMap
import com.app.dealspot.presentation.ui.components.TopBar
import com.app.dealspot.presentation.ui.home.search_provide_for_service.selection.ServiceSelectionField
import com.app.dealspot.presentation.ui.home.search_provide_for_service.selection.ServiceSelectionSheet
import com.app.dealspot.presentation.view.DealPublishBottomSheet
import com.app.dealspot.presentation.view.DealSpotDarkButton
import com.app.dealspot.presentation.view.DealSpotTextInputFieldWithInnerPlaceholderText
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.describe_services_you_provide
import dealspot.composeapp.generated.resources.describe_your_service
import dealspot.composeapp.generated.resources.my_specialization
import dealspot.composeapp.generated.resources.provide_service
import dealspot.composeapp.generated.resources.publish
import dealspot.composeapp.generated.resources.service_electrician
import dealspot.composeapp.generated.resources.what_service_do_you_need
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun ProvideServiceScreen(
    viewModel: ProvideServiceViewModel = koinInject(),
    onBackClicked: () -> Unit = {}
) {
    var showSelectionSheet by remember { mutableStateOf(false) }
    var miniMapCameraState by remember { mutableStateOf<MapCameraState?>(null) }
    var userSelectedLocation by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var isMapGestureActive by remember { mutableStateOf(false) }
    
    // Local state for form fields to ensure button state updates reactively
    var specialization by remember { mutableStateOf("") }
    var serviceDescription by remember { mutableStateOf("") }
    
    // ViewModel state
    val isLoading by viewModel.isLoading.collectAsState()
    val showBottomSheet by viewModel.showBottomSheet.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimens_60, bottom = dimens_50),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState, enabled = !isMapGestureActive)
                    .padding(horizontal = dimens_20),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar(
                    title = stringResource(Res.string.provide_service),
                    screenType = ScreenType.PROVIDE_SERVICE,
                    onBackClicked = { onBackClicked.invoke() }
                )

                SpacerHeight25Dp()

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(Res.string.my_specialization),
                    fontSize = text_size_16,
                    color = Grey,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerHeight5Dp()

                DealSpotTextInputFieldWithInnerPlaceholderText(
                    modifier = Modifier,
                    placeHolderText = stringResource(Res.string.service_electrician),
                    imeAction = ImeAction.Done,
                    labelTextColor = grey_700_70_transparent,
                    prevValue = specialization
                ) { spec ->
                    specialization = spec
                    viewModel.setSpecialization(spec = spec)
                }

                SpacerHeight25Dp()

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(Res.string.describe_your_service),
                    fontSize = text_size_16,
                    color = Grey,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerHeight5Dp()

                DealSpotTextInputFieldWithInnerPlaceholderText(
                    modifier = Modifier.height(dimens_100),
                    placeHolderText = stringResource(Res.string.describe_services_you_provide),
                    isSingleLine = false,
                    labelTextColor = grey_700_70_transparent,
                    imeAction = ImeAction.Default,
                    prevValue = serviceDescription
                ) { description ->
                    serviceDescription = description
                    viewModel.setServiceDescription(description = description)
                }

                SpacerHeight25Dp()

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(Res.string.what_service_do_you_need),
                    fontSize = text_size_16,
                    color = Grey,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerHeight10Dp()

                ServiceSelectionField(
                    selectedCategory = viewModel.selectedCategory,
                    selectedService = viewModel.selectedService,
                    onClick = { showSelectionSheet = true }
                )

                SpacerHeight25Dp()

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "Поставте маркер, якщо хочете змінити локацію",
                    fontSize = text_size_16,
                    color = Grey,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerHeight10Dp()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens_300)
                        .border(width = dimens_1, color = grey_middle, shape = RoundedCornerShape(dimens_5))
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    val anyPressed = event.changes.any { it.pressed }
                                    if (isMapGestureActive != anyPressed) {
                                        isMapGestureActive = anyPressed
                                    }
                                }
                            }
                        }
                ) {
                    SelectableMap(
                        modifier = Modifier.fillMaxSize(),
                        selectedPosition = viewModel.selectedLocation,
                        onMapClick = { latLng ->
                            userSelectedLocation = true
                            viewModel.setLocation(location = latLng)
                            miniMapCameraState = MapCameraState(
                                latitude = latLng.latitude,
                                longitude = latLng.longitude,
                                zoom = miniMapCameraState?.zoom ?: 15f
                            )
                        },
                        onLocationAvailable = { latLng ->
                            if (!userSelectedLocation) {
                                viewModel.setLocation(location = latLng)
                                miniMapCameraState = MapCameraState(
                                    latitude = latLng.latitude,
                                    longitude = latLng.longitude,
                                    zoom = miniMapCameraState?.zoom ?: 14f
                                )
                            }
                        }
                    )
                }

                SpacerHeight25Dp()

                DealSpotDarkButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(Res.string.publish),
                    isEnable = !isLoading && specialization.isNotBlank() && 
                               serviceDescription.isNotBlank() && 
                               viewModel.selectedCategory != null && 
                               viewModel.selectedService != null,
                    onClick = {
                        println("ProvideServiceScreen. Create button clicked")
                        viewModel.publishDeal()
                    }
                )

                SpacerHeight25Dp()
            }
        }

        ServiceSelectionSheet(
            visible = showSelectionSheet,
            selectedCategory = viewModel.selectedCategory,
            selectedService = viewModel.selectedService,
            onDismissRequest = { showSelectionSheet = false },
            onServiceSelected = { category, service ->
                viewModel.setCategoryInfo(category = category, service = service)
                showSelectionSheet = false
            }
        )
        
        // Publish bottom sheet
        DealPublishBottomSheet(
            visible = showBottomSheet,
            isLoading = isLoading,
            onOkClicked = {
                viewModel.closeBottomSheet()
                onBackClicked()
            }
        )
    }
}