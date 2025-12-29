package com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service

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
import com.app.dealspot.presentation.view.HorizontalThicknessDividerAlpha008
import com.app.dealspot.presentation.view.ToggleWithLeftText
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.describe_the_problem
import dealspot.composeapp.generated.resources.looking_for_service
import dealspot.composeapp.generated.resources.problem_description_example
import dealspot.composeapp.generated.resources.problem_example_washing_machine
import dealspot.composeapp.generated.resources.problem_that_needs_to_be_solved
import dealspot.composeapp.generated.resources.publish
import dealspot.composeapp.generated.resources.urgent_problem
import dealspot.composeapp.generated.resources.what_service_do_you_need
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import com.app.dealspot.business.AppDataStore
import com.app.dealspot.presentation.view.WhiteBackground

@Composable
fun LookingForServiceScreen(
    viewModel: LookingForServiceViewModel = koinInject(),
    appDataStore: AppDataStore = koinInject(),
    onBackClicked: () -> Unit = {}
) {
    var showSelectionSheet by remember { mutableStateOf(false) }
    var userSelectedLocation by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var isMapGestureActive by remember { mutableStateOf(false) }
    
    // Local state for form fields to ensure button state updates reactively
    var problemName by remember { mutableStateOf("") }
    var problemDescription by remember { mutableStateOf("") }
    
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
                    title = stringResource(Res.string.looking_for_service),
                    screenType = ScreenType.LOOKING_FOR_SERVICE,
                    onBackClicked = {
                        onBackClicked.invoke()
                    }
                )

                SpacerHeight25Dp()

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(Res.string.problem_that_needs_to_be_solved),
                    fontSize = text_size_16,
                    color = Grey,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerHeight5Dp()

                DealSpotTextInputFieldWithInnerPlaceholderText(
                    modifier = Modifier,
                    placeHolderText = stringResource(Res.string.problem_example_washing_machine),
                    imeAction = ImeAction.Done,
                    labelTextColor = grey_700_70_transparent,
                    prevValue = problemName
                ) { name ->
                    println("LookingForServiceScreen. Problem name: $name")
                    println("LookingForServiceScreen. selectedCategory: ${viewModel.selectedCategory?.name}")
                    problemName = name
                    viewModel.setName(name = name)
                }

                SpacerHeight25Dp()

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(Res.string.describe_the_problem),
                    fontSize = text_size_16,
                    color = Grey,
                    fontWeight = FontWeight.W600,
                    fontFamily = latoFontFamily()
                )

                SpacerHeight5Dp()

                DealSpotTextInputFieldWithInnerPlaceholderText(
                    modifier = Modifier.height(dimens_100),
                    placeHolderText = stringResource(Res.string.problem_description_example),
                    isSingleLine = false,
                    labelTextColor = grey_700_70_transparent,
                    imeAction = ImeAction.Done,
                    prevValue = problemDescription
                ) { description ->
                    println("LookingForServiceScreen. Problem description: $description")
                    println("LookingForServiceScreen. selectedCategory: ${viewModel.selectedCategory?.name}")
                    problemDescription = description
                    viewModel.setDescription(description = description)
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

                /** Toggle section */
                HorizontalThicknessDividerAlpha008()

                ToggleWithLeftText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    toggleText = stringResource(Res.string.urgent_problem)
                ) { isServiceUrgent ->
                    println("ToggleWithLeftText. isServiceUrgent: $isServiceUrgent")
                    viewModel.setUrgent(urgent = isServiceUrgent)
                }

                HorizontalThicknessDividerAlpha008()

                SpacerHeight25Dp()

                /** Text title for map */
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
                            println("MiniMap onMapClick lat=${latLng.latitude}, lng=${latLng.longitude}")
                            userSelectedLocation = true
                            viewModel.setLocation(location = latLng)
                        },
                        onLocationAvailable = { latLng ->
                            if (!userSelectedLocation) {
                                println("MiniMap onLocationAvailable lat=${latLng.latitude}, lng=${latLng.longitude}")
                                viewModel.setLocation(location = latLng)
                            }
                        }
                    )
                }

                SpacerHeight25Dp()

                DealSpotDarkButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttonText = stringResource(Res.string.publish),
                    isEnable = !isLoading && problemName.isNotBlank() && problemDescription.isNotBlank() && viewModel.selectedCategory != null,
                    onClick = {
                        println("LookingForServiceScreen. Create button clicked")
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