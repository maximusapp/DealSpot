package com.app.dealspot.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.dealspot.Greeting
import com.app.dealspot.common.Context
import com.app.dealspot.di.appModule
import com.app.dealspot.presentation.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.apple
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App(context: Context) {
    KoinApplication(application = {
        modules(appModule(context))
    }) {
        AppTheme {
            var showContent by remember { mutableStateOf(false) }
            // Test comment
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(painterResource(Res.drawable.apple), null)
                        Text("Compose: $greeting")
                    }
                }
            }
        }
    }
}