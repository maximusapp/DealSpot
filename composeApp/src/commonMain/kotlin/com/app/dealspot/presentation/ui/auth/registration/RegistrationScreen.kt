package com.app.dealspot.presentation.ui.auth.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.app.dealspot.presentation.theme.dimens_10
import com.app.dealspot.presentation.theme.dimens_100
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_70
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.dimens_80
import com.app.dealspot.presentation.theme.grey_light
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.text_size_24
import com.app.dealspot.presentation.view.DealSpotOutlineButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_deal_spot
import dealspot.composeapp.generated.resources.ic_lock
import dealspot.composeapp.generated.resources.registration
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

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
                onFirstName = viewModel::setFirstName,
                onLastName = viewModel::setLastName,
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

            OutlinedButton(
                onClick = { viewModel.prevStep() },
                enabled = activeStep > 1
            ) {
                Text("Prev")
            }

            val canProceed = when (activeStep) {
                1 -> step1.isValid
                2 -> step2.isValid
                else -> step3.isValid
            }

            Button(
                onClick = { if (activeStep < 3) viewModel.nextStep() else { /* submit */ } },
                enabled = canProceed,
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(if (activeStep < 3) "Next" else "Finish")
            }
        }

        SpacerHeight50Dp()
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
    onFirstName: (String) -> Unit,
    onLastName: (String) -> Unit,
    onAge: (String) -> Unit,
    onGender: (GenderType) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AvatarPicker(currentUri = state.avatarUri, onPick = onAvatarPick)
        SpacerHeight10Dp()
//        OutlinedTextField(value = state.firstName, onValueChange = onFirstName, label = { Text("First name") }, modifier = Modifier.fillMaxWidth())

        DealSpotTextInputField(
            Modifier.fillMaxWidth(),
            placeHolderText = "First name",
            leftIcon = Res.drawable.ic_lock,
            prevValue = state.firstName
        )
        { firstName ->
            println("RegistrationScreen. First name: $firstName")
            onFirstName.invoke(firstName)
        }

        SpacerHeight10Dp()

//        OutlinedTextField(value = state.lastName, onValueChange = onLastName, label = { Text("Last name") }, modifier = Modifier.fillMaxWidth())
        DealSpotTextInputField(
            Modifier.fillMaxWidth(),
            placeHolderText = "Last name",
            leftIcon = Res.drawable.ic_lock,
            prevValue = state.lastName
        )
        { lastName ->
            println("RegistrationScreen. LastName name: $lastName")
            onLastName.invoke(lastName)
        }

        SpacerHeight10Dp()

//        OutlinedTextField(value = state.age, onValueChange = onAge, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
        DealSpotTextInputField(
            Modifier.fillMaxWidth(),
            placeHolderText = "Age",
            leftIcon = Res.drawable.ic_lock,
            prevValue = state.age,
            keyboardType = KeyboardType.Number
        )
        { age ->
            println("RegistrationScreen. Age: $age")
            onAge.invoke(age)
        }

        SpacerHeight20Dp()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val selected = state.gender.ordinal

            DealSpotOutlineButton(
                modifier = Modifier.weight(1f),
                buttonText = "Male",
                enabled = selected != GenderType.MALE.ordinal
            ) {
                onGender(GenderType.MALE)
            }

            DealSpotOutlineButton(
                modifier = Modifier.weight(1f),
                buttonText = "Female",
                enabled = selected != GenderType.FEMALE.ordinal
            ) {
                onGender(GenderType.FEMALE)
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