package com.app.dealspot.presentation.ui.home.search_provide_for_service.looking_for_service

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.app.dealspot.business.ScreenType
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight25Dp
import com.app.dealspot.presentation.theme.SpacerHeight5Dp
import com.app.dealspot.presentation.theme.dimens_100
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_50
import com.app.dealspot.presentation.theme.dimens_60
import com.app.dealspot.presentation.theme.grey_700_70_transparent
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.ui.components.TopBar
import com.app.dealspot.presentation.ui.home.search_provide_for_service.selection.ServiceSelectionField
import com.app.dealspot.presentation.ui.home.search_provide_for_service.selection.ServiceSelectionSheet
import com.app.dealspot.presentation.view.DealSpotTextInputFieldWithInnerPlaceholderText
import com.app.dealspot.presentation.view.ToggleWithLeftText
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.describe_the_problem
import dealspot.composeapp.generated.resources.looking_for_service
import dealspot.composeapp.generated.resources.problem_description_example
import dealspot.composeapp.generated.resources.problem_example_washing_machine
import dealspot.composeapp.generated.resources.problem_that_needs_to_be_solved
import dealspot.composeapp.generated.resources.urgent_problem
import dealspot.composeapp.generated.resources.what_service_do_you_need
import org.jetbrains.compose.resources.stringResource

@Composable
fun LookingForServiceScreen(
    onBackClicked: () -> Unit = {}
) {
    var problemName by remember { mutableStateOf("") }
    var problemDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedService by remember { mutableStateOf<String?>(null) }
    var showSelectionSheet by remember { mutableStateOf(false) }
    var isUrgent by remember { mutableStateOf(false) }

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
                modifier = Modifier.fillMaxSize().padding(horizontal = dimens_20),
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
                    labelTextColor = grey_700_70_transparent
                ) { name ->
                    problemName = name
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
                    imeAction = ImeAction.Done
                ) { description ->
                    problemDescription = description
                }

                SpacerHeight10Dp()

                ToggleWithLeftText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    toggleText = stringResource(Res.string.urgent_problem)
                ) { isServiceUrgent ->
                    println("ToggleWithLeftText. isServiceUrgent: $isServiceUrgent")
                    isUrgent = isServiceUrgent
                }

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