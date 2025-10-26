package com.app.dealspot.presentation.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.app.dealspot.presentation.theme.BorderColor
import com.app.dealspot.presentation.theme.SpacerWidth5Dp
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_42
import com.app.dealspot.presentation.theme.dimens_5
import com.app.dealspot.presentation.theme.grey_700
import com.app.dealspot.presentation.theme.text_size_20

@Composable
fun SixDigitsView(
    enteredCode: (List<String>) -> Unit = {}
) {
    var code by remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = List(6) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        code.forEachIndexed { index, digit ->
            Box(
                modifier = Modifier.width(dimens_42)
                    .height(dimens_42)
                    .border(
                        width = dimens_1,
                        color = BorderColor,
                        shape = RoundedCornerShape(dimens_5)
                    ),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = digit,
                    onValueChange = { value ->
                        if (value.length <= 1 && value.all { it.isDigit() }) {
                            val newCode = code.toMutableList()
                            val previousValue = newCode[index]
                            newCode[index] = value
                            code = newCode
                            enteredCode.invoke(newCode)

                            // Move focus forward when entering a digit
                            if (value.isNotEmpty() && index < 5) {
                                focusRequesters.getOrNull(index + 1)?.requestFocus()
                            }

                            // Move focus backward when deleting a digit
                            if (value.isEmpty() && previousValue.isNotEmpty() && index > 0) {
                                focusRequesters.getOrNull(index - 1)?.requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequesters[index])
                        .fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = text_size_20,
                        color = grey_700
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            SpacerWidth5Dp()
        }
    }
}