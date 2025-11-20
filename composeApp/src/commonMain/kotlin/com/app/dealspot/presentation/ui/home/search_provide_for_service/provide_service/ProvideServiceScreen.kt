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
import com.app.dealspot.data.model.LatLngEntity
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

@Composable
fun ProvideServiceScreen(
    onBackClicked: () -> Unit = {}
) {
    var specialization by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedService by remember { mutableStateOf<String?>(null) }
    var showSelectionSheet by remember { mutableStateOf(false) }
    var selectedLocation by remember { mutableStateOf<LatLngEntity?>(null) }
    var miniMapCameraState by remember { mutableStateOf<MapCameraState?>(null) }
    var userSelectedLocation by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var isMapGestureActive by remember { mutableStateOf(false) }

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
                    labelTextColor = grey_700_70_transparent
                ) { specialization = it }

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
                    imeAction = ImeAction.Default
                ) {
                    description = it
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
                    selectedCategory = selectedCategory,
                    selectedService = selectedService,
                    onClick = { showSelectionSheet = true }
                )

                SpacerHeight25Dp()

                androidx.compose.material3.Text(
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
                        selectedPosition = selectedLocation,
                        onMapClick = { latLng ->
                            userSelectedLocation = true
                            selectedLocation = latLng
                            miniMapCameraState = MapCameraState(
                                latitude = latLng.latitude,
                                longitude = latLng.longitude,
                                zoom = miniMapCameraState?.zoom ?: 15f
                            )
                        },
                        onLocationAvailable = { latLng ->
                            if (!userSelectedLocation) {
                                selectedLocation = latLng
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
                    onClick = {
                        // TODO: Handle publish for provide service
                    }
                )

                SpacerHeight25Dp()
            }
        }

        ServiceSelectionSheet(
            visible = showSelectionSheet,
            selectedCategory = selectedCategory,
            selectedService = selectedService,
            onDismissRequest = { showSelectionSheet = false },
            onServiceSelected = { category, service ->
                selectedCategory = category
                selectedService = service
                showSelectionSheet = false
            }
        )
    }
}