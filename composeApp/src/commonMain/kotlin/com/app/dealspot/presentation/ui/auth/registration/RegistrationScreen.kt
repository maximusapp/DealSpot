package com.app.dealspot.presentation.ui.auth.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.dealspot.business.GenderType
import com.app.dealspot.business.Step1
import com.app.dealspot.business.Step2
import com.app.dealspot.business.Step3
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight10Dp
import com.app.dealspot.presentation.theme.SpacerHeight15Dp
import com.app.dealspot.presentation.theme.SpacerHeight20Dp
import com.app.dealspot.presentation.theme.SpacerHeight50Dp
import com.app.dealspot.presentation.theme.SpacerHeight60Dp
import com.app.dealspot.presentation.theme.SpacerWidth10Dp
import com.app.dealspot.presentation.theme.blueSplashText
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_100
import com.app.dealspot.presentation.theme.dimens_110
import com.app.dealspot.presentation.theme.dimens_15
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_25
import com.app.dealspot.presentation.theme.dimens_40
import com.app.dealspot.presentation.theme.dimens_70
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.dimens_80
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.grey_light
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_14
import com.app.dealspot.presentation.theme.text_size_16
import com.app.dealspot.presentation.theme.text_size_18
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.view.DealSpotOutlineButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_calendar_month
import dealspot.composeapp.generated.resources.ic_deal_spot
import dealspot.composeapp.generated.resources.ic_female
import dealspot.composeapp.generated.resources.ic_lock
import dealspot.composeapp.generated.resources.ic_male
import dealspot.composeapp.generated.resources.ic_person
import dealspot.composeapp.generated.resources.registration
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import kotlin.collections.get

@Preview
@Composable
fun RegistrationScreen() {
    val viewModel: RegistrationViewModel = koinInject()
    val activeStep by viewModel.activeStep.collectAsState()
    val step1 by viewModel.step1.collectAsState()
    val step2 by viewModel.step2.collectAsState()
    val step3 by viewModel.step3.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimens_20),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerHeight50Dp()

        Image(
            painter = painterResource(Res.drawable.ic_deal_spot),
            contentDescription = null,
            modifier = Modifier
                .height(dimens_70)
                .width(dimens_70)
                .clip(RoundedCornerShape(16.dp))
        )

        SpacerHeight15Dp()

        Text(
            text = stringResource(Res.string.registration),
            fontSize = text_size_24,
            color = Grey,
            fontWeight = FontWeight.W700,
            fontFamily = latoFontFamily(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimens_20))

        StepProgress(activeStep = activeStep)

        Spacer(modifier = Modifier.height(dimens_20))

        when (activeStep) {
            1 -> StepOneContent(
                state = step1,
                onAvatarPick = { viewModel.setAvatar(it) },
                onFullName = viewModel::setFirstName,
                onAge = viewModel::setAge,
                onGender = viewModel::setGender
            )
            2 -> StepTwoContent(
                state = step2,
                onEmail = viewModel::setEmail,
                onPhone = viewModel::setPhone
            )
            else -> StepThreeContent(
                state = step3,
                onPassword = viewModel::setPassword,
                onConfirm = viewModel::setConfirmPassword
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            DealSpotOutlineButton(
                modifier = Modifier.width(dimens_110),
                enable = activeStep > 1,
                buttonText = "Prev",
                textSize = text_size_14,
                buttonHeight = dimens_40
            ) {
                viewModel.prevStep()
            }

            val canProceed = when (activeStep) {
                1 -> step1.isValid
                2 -> step2.isValid
                else -> step3.isValid
            }

            DealSpotOutlineButton(
                modifier = Modifier.width(dimens_110),
                enable = canProceed,
                buttonText = if (activeStep < 3) "Next" else "Finish",
                textSize = text_size_14,
                buttonHeight = dimens_40
            ) {
                if (activeStep < 3) viewModel.nextStep() else { /* submit */ }
            }
        }

        SpacerHeight60Dp()
    }
}

@Composable
private fun StepProgress(activeStep: Int) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(3) { index ->
            val stepIndex = index + 1
            val color = when {
                stepIndex < activeStep -> Color(0xFF3cb371)
                stepIndex == activeStep -> DealSpotDark
                else -> grey_light
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(dimens_10)
                    .padding(horizontal = dimens_8)
                    .background(color, shape = CircleShape)
            )
        }
    }
}

@Composable
private fun StepOneContent(
    state: Step1,
    onAvatarPick: (String) -> Unit,
    onFullName: (String) -> Unit,
    onAge: (String) -> Unit,
    onGender: (GenderType) -> Unit
) {
    val genders = GenderType.entries.toList()
    var selectedGender by remember { mutableStateOf<GenderType?>(null) }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AvatarPicker(currentUri = state.avatarUri, onPick = onAvatarPick)

        SpacerHeight10Dp()

        DealSpotTextInputField(
            Modifier.fillMaxWidth(),
            placeHolderText = "Full name",
            leftIcon = Res.drawable.ic_person,
            prevValue = state.fullName
        )
        { fullName ->
            println("RegistrationScreen. Full name: $fullName")
            onFullName.invoke(fullName)
        }

        SpacerHeight10Dp()

        SpacerHeight10Dp()

        DealSpotTextInputField(
            Modifier.fillMaxWidth(),
            placeHolderText = "Age",
            leftIcon = Res.drawable.ic_calendar_month,
            prevValue = state.age,
            keyboardType = KeyboardType.Number
        )
        { age ->
            println("RegistrationScreen. Age: $age")
            onAge.invoke(age)
        }

        SpacerHeight20Dp()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val selected = state.gender?.ordinal
            if (selected != null) {
                selectedGender = genders[selected]
            }

            // Debug: Print genders once when the composable is created/recomposed
            println("Available genders: ${genders.joinToString { it.name }}")

            for(gender in genders) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedGender = gender
                            println("Registration screen. Selected gender: $selectedGender")
                            onGender(gender)
                        },
                    color = if (selectedGender?.ordinal == gender.ordinal) DealSpotDark else grey_light,
                    shape = RoundedCornerShape(dimens_8)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dimens_15, horizontal = dimens_25),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val icon = when (gender.ordinal) {
                            GenderType.MALE.ordinal -> Res.drawable.ic_male
                            GenderType.FEMALE.ordinal -> Res.drawable.ic_female
                            else -> Res.drawable.ic_male
                        }
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "Gender icon",
                            modifier = Modifier.size(dimens_25),
                            tint = if (selectedGender?.ordinal == gender.ordinal) Color.White else grey_700
                        )
                        SpacerWidth10Dp()

                        Text(
                            text = stringResource(gender.displayName),
                            color = if (selectedGender?.ordinal == gender.ordinal) Color.White else grey_700,
                            fontSize = text_size_18,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = latoFontFamily()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StepTwoContent(
    state: Step2,
    onEmail: (String) -> Unit,
    onPhone: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = state.email, onValueChange = onEmail, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = state.phone, onValueChange = onPhone, label = { Text("Phone") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun StepThreeContent(
    state: Step3,
    onPassword: (String) -> Unit,
    onConfirm: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = state.password, onValueChange = onPassword, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = state.confirmPassword, onValueChange = onConfirm, label = { Text("Confirm password") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
expect fun AvatarPicker(currentUri: String, onPick: (String) -> Unit)