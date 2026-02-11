package com.app.dealspot.presentation.utils

import androidx.compose.ui.input.key.Key.Companion.R
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_building
import dealspot.composeapp.generated.resources.ic_car_service
import dealspot.composeapp.generated.resources.ic_creative
import dealspot.composeapp.generated.resources.ic_finance
import dealspot.composeapp.generated.resources.ic_food
import dealspot.composeapp.generated.resources.ic_health
import dealspot.composeapp.generated.resources.ic_household
import dealspot.composeapp.generated.resources.ic_legal
import dealspot.composeapp.generated.resources.ic_lifestyle
import dealspot.composeapp.generated.resources.ic_other
import dealspot.composeapp.generated.resources.ic_software
import dealspot.composeapp.generated.resources.ic_technical
import org.jetbrains.compose.resources.DrawableResource

fun serviceIcon(serviceId: Int): DrawableResource {
    return when (serviceId) {
        1 -> Res.drawable.ic_household
        2 -> Res.drawable.ic_technical
        3 -> Res.drawable.ic_car_service
        4 -> Res.drawable.ic_building
        5 -> Res.drawable.ic_software
        6 -> Res.drawable.ic_lifestyle
        7 -> Res.drawable.ic_health
        8 -> Res.drawable.ic_food
        9 -> Res.drawable.ic_finance
        10 -> Res.drawable.ic_legal
        11 -> Res.drawable.ic_creative
        12 -> Res.drawable.ic_other
        else -> Res.drawable.ic_technical
    }
}