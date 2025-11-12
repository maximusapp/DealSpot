package com.app.dealspot.presentation.ui.home.search_provide_for_service.selection

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.DevicesOther
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.dealspot.data.model.ServiceCategoryEntity
import com.app.dealspot.data.model.ServiceEntity
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight12Dp
import com.app.dealspot.presentation.theme.SpacerWidth15Dp
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.dimens_16
import com.app.dealspot.presentation.theme.dimens_200
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.grey_middle
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.white
import com.app.dealspot.presentation.view.DealSpotOutlineButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

private val SheetShape = RoundedCornerShape(dimens_16)

@Composable
private fun getServiceCategories(): List<ServiceCategoryEntity> {
    // Categories ordered by priority - most popular and frequently needed first
    return listOf(
    // 1. Household appliances - Most common daily repairs (washing machines, refrigerators, etc.)
    ServiceCategoryEntity(
        id = 1,
        name = stringResource(Res.string.category_household_appliances),
        icon = Icons.Outlined.Kitchen,
        services = listOf(
            ServiceEntity(id = 1, categoryId = 1, name = stringResource(Res.string.service_all_home_appliance_repair_services)),
            ServiceEntity(id = 2, categoryId = 1, name = stringResource(Res.string.service_deep_fryer_repair)),
            ServiceEntity(id = 3, categoryId = 1, name = stringResource(Res.string.service_kitchen_hood_repair)),
            ServiceEntity(id = 4, categoryId = 1, name = stringResource(Res.string.service_grill_repair)),
            ServiceEntity(id = 5, categoryId = 1, name = stringResource(Res.string.service_water_cooler_cleaning)),
            ServiceEntity(id = 6, categoryId = 1, name = stringResource(Res.string.service_air_conditioner_cleaning)),
            ServiceEntity(id = 7, categoryId = 1, name = stringResource(Res.string.service_water_heater_boiler_cleaning)),
            ServiceEntity(id = 8, categoryId = 1, name = stringResource(Res.string.service_sewing_machine_repair)),
            ServiceEntity(id = 9, categoryId = 1, name = stringResource(Res.string.service_refrigeration_equipment_display_case_repair)),
            ServiceEntity(id = 10, categoryId = 1, name = stringResource(Res.string.service_refrigerator_repair)),
            ServiceEntity(id = 11, categoryId = 1, name = stringResource(Res.string.service_bread_maker_repair)),
            ServiceEntity(id = 12, categoryId = 1, name = stringResource(Res.string.service_camera_repair)),
            ServiceEntity(id = 13, categoryId = 1, name = stringResource(Res.string.service_hair_dryer_repair)),
            ServiceEntity(id = 14, categoryId = 1, name = stringResource(Res.string.service_dough_mixer_repair)),
            ServiceEntity(id = 16, categoryId = 1, name = stringResource(Res.string.service_telephone_repair)),
            ServiceEntity(id = 17, categoryId = 1, name = stringResource(Res.string.service_television_repair)),
            ServiceEntity(id = 18, categoryId = 1, name = stringResource(Res.string.service_dryer_repair)),
            ServiceEntity(id = 20, categoryId = 1, name = stringResource(Res.string.service_pressure_cooker_repair)),
            ServiceEntity(id = 21, categoryId = 1, name = stringResource(Res.string.service_tv_remote_control_repair)),
            ServiceEntity(id = 22, categoryId = 1, name = stringResource(Res.string.service_projector_repair)),
            ServiceEntity(id = 23, categoryId = 1, name = stringResource(Res.string.service_iron_repair)),
            ServiceEntity(id = 24, categoryId = 1, name = stringResource(Res.string.service_washing_machine_repair)),
            ServiceEntity(id = 25, categoryId = 1, name = stringResource(Res.string.service_dishwasher_repair)),
            ServiceEntity(id = 26, categoryId = 1, name = stringResource(Res.string.service_vacuum_cleaner_repair)),
            ServiceEntity(id = 27, categoryId = 1, name = stringResource(Res.string.service_overlock_machine_repair)),
            ServiceEntity(id = 28, categoryId = 1, name = stringResource(Res.string.service_instant_water_heater_repair)),
            ServiceEntity(id = 29, categoryId = 1, name = stringResource(Res.string.service_multicooker_repair)),
            ServiceEntity(id = 30, categoryId = 1, name = stringResource(Res.string.service_music_center_repair)),
            ServiceEntity(id = 31, categoryId = 1, name = stringResource(Res.string.service_freezer_repair)),
            ServiceEntity(id = 32, categoryId = 1, name = stringResource(Res.string.service_microwave_oven_repair)),
            ServiceEntity(id = 33, categoryId = 1, name = stringResource(Res.string.service_ice_maker_repair)),
            ServiceEntity(id = 34, categoryId = 1, name = stringResource(Res.string.service_kitchen_food_processor_repair)),
            ServiceEntity(id = 35, categoryId = 1, name = stringResource(Res.string.service_water_cooler_repair)),
            ServiceEntity(id = 36, categoryId = 1, name = stringResource(Res.string.service_air_conditioner_repair)),
            ServiceEntity(id = 37, categoryId = 1, name = stringResource(Res.string.service_space_heater_repair)),
            ServiceEntity(id = 38, categoryId = 1, name = stringResource(Res.string.service_coffee_maker_repair)),
            ServiceEntity(id = 39, categoryId = 1, name = stringResource(Res.string.service_induction_cooktop_repair)),
            ServiceEntity(id = 40, categoryId = 1, name = stringResource(Res.string.service_electric_stove_repair)),
            ServiceEntity(id = 41, categoryId = 1, name = stringResource(Res.string.service_electric_cooktop_repair)),
            ServiceEntity(id = 42, categoryId = 1, name = stringResource(Res.string.service_electric_meat_grinder_repair)),
            ServiceEntity(id = 43, categoryId = 1, name = stringResource(Res.string.service_electric_fireplace_repair)),
            ServiceEntity(id = 44, categoryId = 1, name = stringResource(Res.string.service_oven_repair)),
            ServiceEntity(id = 45, categoryId = 1, name = stringResource(Res.string.service_home_audio_system_repair)),
            ServiceEntity(id = 46, categoryId = 1, name = stringResource(Res.string.service_home_theater_repair)),
            ServiceEntity(id = 47, categoryId = 1, name = stringResource(Res.string.service_cooktop_repair)),
            ServiceEntity(id = 48, categoryId = 1, name = stringResource(Res.string.service_boiler_repair)),
            ServiceEntity(id = 49, categoryId = 1, name = stringResource(Res.string.service_blender_repair)),
            ServiceEntity(id = 50, categoryId = 1, name = stringResource(Res.string.service_antenna_repair)),
            ServiceEntity(id = 51, categoryId = 1, name = stringResource(Res.string.service_tuner_firmware_update)),
            ServiceEntity(id = 52, categoryId = 1, name = stringResource(Res.string.service_television_dismantling)),
            ServiceEntity(id = 53, categoryId = 1, name = stringResource(Res.string.service_dryer_dismantling)),
            ServiceEntity(id = 54, categoryId = 1, name = stringResource(Res.string.service_washing_machine_dismantling)),
            ServiceEntity(id = 55, categoryId = 1, name = stringResource(Res.string.service_dishwasher_dismantling)),
            ServiceEntity(id = 56, categoryId = 1, name = stringResource(Res.string.service_air_conditioner_dismantling)),
            ServiceEntity(id = 57, categoryId = 1, name = stringResource(Res.string.service_cooktop_dismantling)),
            ServiceEntity(id = 58, categoryId = 1, name = stringResource(Res.string.service_boiler_dismantling))
        )
    ),
    // 2. Skilled Trades & Technical Services - Electricians, plumbers, handymen (very common)
    ServiceCategoryEntity(
        id = 2,
        name = stringResource(Res.string.category_skilled_trades_technical_services),
        icon = Icons.Outlined.Construction,
        services = listOf(
            ServiceEntity(id = 59, categoryId = 2, name = stringResource(Res.string.service_electrician)),
            ServiceEntity(id = 60, categoryId = 2, name = stringResource(Res.string.service_plumber)),
            ServiceEntity(id = 61, categoryId = 2, name = stringResource(Res.string.service_carpenter)),
            ServiceEntity(id = 62, categoryId = 2, name = stringResource(Res.string.service_welder)),
            ServiceEntity(id = 63, categoryId = 2, name = stringResource(Res.string.service_painter)),
            ServiceEntity(id = 64, categoryId = 2, name = stringResource(Res.string.service_locksmith)),
            ServiceEntity(id = 65, categoryId = 2, name = stringResource(Res.string.service_solar_panel)),
            ServiceEntity(id = 66, categoryId = 2, name = stringResource(Res.string.service_handyman)),
            ServiceEntity(id = 67, categoryId = 2, name = stringResource(Res.string.service_heating_ventilation_air_conditioning)),
            ServiceEntity(id = 68, categoryId = 2, name = stringResource(Res.string.service_gas_fitter)),
            ServiceEntity(id = 69, categoryId = 2, name = stringResource(Res.string.service_refrigeration_mechanic)),
            ServiceEntity(id = 70, categoryId = 2, name = stringResource(Res.string.service_boiler_technician)),
            ServiceEntity(id = 71, categoryId = 2, name = stringResource(Res.string.service_water_treatment_installer)),
            ServiceEntity(id = 72, categoryId = 2, name = stringResource(Res.string.service_fire_protection_installer)),
            ServiceEntity(id = 73, categoryId = 2, name = stringResource(Res.string.service_elevator_specialist)),
            ServiceEntity(id = 74, categoryId = 2, name = stringResource(Res.string.service_smart_home_technician)),
            ServiceEntity(id = 75, categoryId = 2, name = stringResource(Res.string.service_security_system_specialist)),
            ServiceEntity(id = 76, categoryId = 2, name = stringResource(Res.string.service_network_installer)),
            ServiceEntity(id = 77, categoryId = 2, name = stringResource(Res.string.service_wind_renewable_energy_technician)),
            ServiceEntity(id = 78, categoryId = 2, name = stringResource(Res.string.service_woodcarver)),
            ServiceEntity(id = 79, categoryId = 2, name = stringResource(Res.string.service_road_surface_installer)),
            ServiceEntity(id = 80, categoryId = 2, name = stringResource(Res.string.service_lighting_specialist))
        )
    ),
    // 3. Auto Services - Car repairs are very common
    ServiceCategoryEntity(
        id = 10,
        name = stringResource(Res.string.category_auto_services),
        icon = Icons.Outlined.DirectionsCar,
        services = listOf(
            ServiceEntity(id = 163, categoryId = 10, name = stringResource(Res.string.service_auto_mechanic)),
            ServiceEntity(id = 164, categoryId = 10, name = stringResource(Res.string.service_auto_electrician)),
            ServiceEntity(id = 165, categoryId = 10, name = stringResource(Res.string.service_other))
        )
    ),
    // 4. Building & Cleaning Services - Cleaning, maintenance, construction
    ServiceCategoryEntity(
        id = 3,
        name = stringResource(Res.string.category_building_cleaning_services),
        icon = Icons.Outlined.CleaningServices,
        services = listOf(
            ServiceEntity(id = 82, categoryId = 3, name = stringResource(Res.string.service_janitor)),
            ServiceEntity(id = 83, categoryId = 3, name = stringResource(Res.string.service_housekeeper)),
            ServiceEntity(id = 84, categoryId = 3, name = stringResource(Res.string.service_hotel_cleaner)),
            ServiceEntity(id = 85, categoryId = 3, name = stringResource(Res.string.service_window_cleaner)),
            ServiceEntity(id = 86, categoryId = 3, name = stringResource(Res.string.service_pest_control_technician)),
            ServiceEntity(id = 87, categoryId = 3, name = stringResource(Res.string.service_waste_collector)),
            ServiceEntity(id = 88, categoryId = 3, name = stringResource(Res.string.service_landscaper)),
            ServiceEntity(id = 89, categoryId = 3, name = stringResource(Res.string.service_gardener)),
            ServiceEntity(id = 90, categoryId = 3, name = stringResource(Res.string.service_tree_surgeon)),
            ServiceEntity(id = 91, categoryId = 3, name = stringResource(Res.string.service_lawn_care_specialist)),
            ServiceEntity(id = 92, categoryId = 3, name = stringResource(Res.string.service_pool_maintenance_technician)),
            ServiceEntity(id = 93, categoryId = 3, name = stringResource(Res.string.service_snow_removal)),
            ServiceEntity(id = 94, categoryId = 3, name = stringResource(Res.string.service_interior_designer)),
            ServiceEntity(id = 95, categoryId = 3, name = stringResource(Res.string.service_interior_decorator)),
            ServiceEntity(id = 96, categoryId = 3, name = stringResource(Res.string.service_bricklayer_mason)),
            ServiceEntity(id = 97, categoryId = 3, name = stringResource(Res.string.service_rebar_installer)),
            ServiceEntity(id = 98, categoryId = 3, name = stringResource(Res.string.service_construction_worker)),
            ServiceEntity(id = 99, categoryId = 3, name = stringResource(Res.string.service_framer)),
            ServiceEntity(id = 100, categoryId = 3, name = stringResource(Res.string.service_drywaller)),
            ServiceEntity(id = 101, categoryId = 3, name = stringResource(Res.string.service_roofer)),
            ServiceEntity(id = 102, categoryId = 3, name = stringResource(Res.string.service_tiler)),
            ServiceEntity(id = 103, categoryId = 3, name = stringResource(Res.string.service_demolition_worker)),
            ServiceEntity(id = 104, categoryId = 3, name = stringResource(Res.string.service_plasterer)),
            ServiceEntity(id = 105, categoryId = 3, name = stringResource(Res.string.service_wallpaper_installer)),
            ServiceEntity(id = 106, categoryId = 3, name = stringResource(Res.string.service_floor_layer)),
            ServiceEntity(id = 107, categoryId = 3, name = stringResource(Res.string.service_ceiling_installer)),
            ServiceEntity(id = 108, categoryId = 3, name = stringResource(Res.string.service_window_installer)),
            ServiceEntity(id = 109, categoryId = 3, name = stringResource(Res.string.service_door_installer)),
            ServiceEntity(id = 110, categoryId = 3, name = stringResource(Res.string.service_glazier)),
            ServiceEntity(id = 111, categoryId = 3, name = stringResource(Res.string.service_joiner)),
            ServiceEntity(id = 112, categoryId = 3, name = stringResource(Res.string.service_kitchen_fitter)),
            ServiceEntity(id = 113, categoryId = 3, name = stringResource(Res.string.service_bathroom_fitter)),
            ServiceEntity(id = 114, categoryId = 3, name = stringResource(Res.string.service_countertop_fabricator)),
            ServiceEntity(id = 115, categoryId = 3, name = stringResource(Res.string.service_stone_mason)),
            ServiceEntity(id = 116, categoryId = 3, name = stringResource(Res.string.service_stair_installer)),
            ServiceEntity(id = 117, categoryId = 3, name = stringResource(Res.string.service_fence_installer)),
            ServiceEntity(id = 118, categoryId = 3, name = stringResource(Res.string.service_irrigation_system_installer)),
            ServiceEntity(id = 119, categoryId = 3, name = stringResource(Res.string.service_epoxy_floor_installer)),
            ServiceEntity(id = 120, categoryId = 3, name = stringResource(Res.string.service_upholstered_furniture_carpet_cleaning))
        )
    ),
    // 5. Technology & Information Services - IT support, computer repairs
    ServiceCategoryEntity(
        id = 9,
        name = stringResource(Res.string.category_technology_information_services),
        icon = Icons.Outlined.Computer,
        services = listOf(
            ServiceEntity(id = 159, categoryId = 9, name = stringResource(Res.string.service_network_administrator)),
            ServiceEntity(id = 160, categoryId = 9, name = stringResource(Res.string.service_cybersecurity_specialist)),
            ServiceEntity(id = 161, categoryId = 9, name = stringResource(Res.string.service_software_engineer)),
            ServiceEntity(id = 162, categoryId = 9, name = stringResource(Res.string.service_repair_maintenance_computer_equipment))
        )
    ),
    // 6. Personal & Lifestyle Services - Haircuts, personal care
    ServiceCategoryEntity(
        id = 5,
        name = stringResource(Res.string.category_personal_lifestyle_services),
        icon = Icons.Outlined.Person,
        services = listOf(
            ServiceEntity(id = 132, categoryId = 5, name = stringResource(Res.string.service_barber)),
            ServiceEntity(id = 133, categoryId = 5, name = stringResource(Res.string.service_hair_stylist)),
            ServiceEntity(id = 134, categoryId = 5, name = stringResource(Res.string.service_makeup_artist)),
            ServiceEntity(id = 135, categoryId = 5, name = stringResource(Res.string.service_nail_technician)),
            ServiceEntity(id = 136, categoryId = 5, name = stringResource(Res.string.service_cosmetologist)),
            ServiceEntity(id = 137, categoryId = 5, name = stringResource(Res.string.service_tattoo_artist)),
            ServiceEntity(id = 138, categoryId = 5, name = stringResource(Res.string.service_seamstress)),
            ServiceEntity(id = 139, categoryId = 5, name = stringResource(Res.string.service_personal_trainer))
        )
    ),
    // 7. Health & Human Services - Medical services
    ServiceCategoryEntity(
        id = 4,
        name = stringResource(Res.string.category_health_human_services),
        icon = Icons.Outlined.HealthAndSafety,
        services = listOf(
            ServiceEntity(id = 121, categoryId = 4, name = stringResource(Res.string.service_doctor)),
            ServiceEntity(id = 122, categoryId = 4, name = stringResource(Res.string.service_nurse)),
            ServiceEntity(id = 123, categoryId = 4, name = stringResource(Res.string.service_physical_therapist)),
            ServiceEntity(id = 124, categoryId = 4, name = stringResource(Res.string.service_speech_therapist)),
            ServiceEntity(id = 125, categoryId = 4, name = stringResource(Res.string.service_psychologist)),
            ServiceEntity(id = 126, categoryId = 4, name = stringResource(Res.string.service_psychiatrist)),
            ServiceEntity(id = 127, categoryId = 4, name = stringResource(Res.string.service_housekeeping_assistant)),
            ServiceEntity(id = 128, categoryId = 4, name = stringResource(Res.string.service_nutritionist_dietitian)),
            ServiceEntity(id = 129, categoryId = 4, name = stringResource(Res.string.service_fitness_trainer)),
            ServiceEntity(id = 130, categoryId = 4, name = stringResource(Res.string.service_massage_therapist)),
            ServiceEntity(id = 131, categoryId = 4, name = stringResource(Res.string.service_veterinarian))
        )
    ),
    // 8. Food & Hospitality Services
    ServiceCategoryEntity(
        id = 6,
        name = stringResource(Res.string.category_food_hospitality_services),
        icon = Icons.Outlined.Restaurant,
        services = listOf(
            ServiceEntity(id = 141, categoryId = 6, name = stringResource(Res.string.service_chef)),
            ServiceEntity(id = 142, categoryId = 6, name = stringResource(Res.string.service_cook)),
            ServiceEntity(id = 143, categoryId = 6, name = stringResource(Res.string.service_baker)),
            ServiceEntity(id = 144, categoryId = 6, name = stringResource(Res.string.service_butcher)),
            ServiceEntity(id = 145, categoryId = 6, name = stringResource(Res.string.service_waiter_waitress)),
            ServiceEntity(id = 146, categoryId = 6, name = stringResource(Res.string.service_bartender)),
            ServiceEntity(id = 147, categoryId = 6, name = stringResource(Res.string.service_barista)),
            ServiceEntity(id = 148, categoryId = 6, name = stringResource(Res.string.service_event_planner)),
            ServiceEntity(id = 149, categoryId = 6, name = stringResource(Res.string.service_wedding_planner)),
            ServiceEntity(id = 150, categoryId = 6, name = stringResource(Res.string.service_tour_guide))
        )
    ),
    // 9. Business, Finance & Administrative Services
    ServiceCategoryEntity(
        id = 7,
        name = stringResource(Res.string.category_business_finance_administrative_services),
        icon = Icons.Outlined.AccountBalance,
        services = listOf(
            ServiceEntity(id = 151, categoryId = 7, name = stringResource(Res.string.service_accountant)),
            ServiceEntity(id = 152, categoryId = 7, name = stringResource(Res.string.service_financial_advisor)),
            ServiceEntity(id = 153, categoryId = 7, name = stringResource(Res.string.service_real_estate_agent))
        )
    ),
    // 10. Legal & Protective Services
    ServiceCategoryEntity(
        id = 8,
        name = stringResource(Res.string.category_legal_protective_services),
        icon = Icons.Outlined.Gavel,
        services = listOf(
            ServiceEntity(id = 154, categoryId = 8, name = stringResource(Res.string.service_lawyer)),
            ServiceEntity(id = 155, categoryId = 8, name = stringResource(Res.string.service_paralegal)),
            ServiceEntity(id = 156, categoryId = 8, name = stringResource(Res.string.service_notary_public)),
            ServiceEntity(id = 157, categoryId = 8, name = stringResource(Res.string.service_security_guard))
        )
    ),
    // 11. Creative, Cultural & Media Services
    ServiceCategoryEntity(
        id = 11,
        name = stringResource(Res.string.category_creative_cultural_media_services),
        icon = Icons.Outlined.Brush,
        services = listOf(
            ServiceEntity(id = 166, categoryId = 11, name = stringResource(Res.string.service_photographer)),
            ServiceEntity(id = 167, categoryId = 11, name = stringResource(Res.string.service_videographer)),
            ServiceEntity(id = 168, categoryId = 11, name = stringResource(Res.string.service_graphic_designer)),
            ServiceEntity(id = 169, categoryId = 11, name = stringResource(Res.string.service_illustrator)),
            ServiceEntity(id = 170, categoryId = 11, name = stringResource(Res.string.service_animator)),
            ServiceEntity(id = 172, categoryId = 11, name = stringResource(Res.string.service_actor)),
            ServiceEntity(id = 173, categoryId = 11, name = stringResource(Res.string.service_musician)),
            ServiceEntity(id = 174, categoryId = 11, name = stringResource(Res.string.service_sound_engineer)),
            ServiceEntity(id = 175, categoryId = 11, name = stringResource(Res.string.service_writer)),
            ServiceEntity(id = 176, categoryId = 11, name = stringResource(Res.string.service_journalist)),
            ServiceEntity(id = 177, categoryId = 11, name = stringResource(Res.string.service_translator))
        )
    ),
    // 12. Other Services - Catch-all category (last)
    ServiceCategoryEntity(
        id = 12,
        name = stringResource(Res.string.category_other_services),
        icon = Icons.Outlined.DevicesOther,
        services = listOf(
            ServiceEntity(id = 178, categoryId = 12, name = stringResource(Res.string.service_walking_pets)),
            ServiceEntity(id = 179, categoryId = 12, name = stringResource(Res.string.service_take_out_trash)),
            ServiceEntity(id = 180, categoryId = 12, name = stringResource(Res.string.service_loader_services)),
            ServiceEntity(id = 181, categoryId = 12, name = stringResource(Res.string.service_cargo_transportation_services)),
            ServiceEntity(id = 182, categoryId = 12, name = stringResource(Res.string.service_furniture_reupholstery_restoration)),
            ServiceEntity(id = 183, categoryId = 12, name = stringResource(Res.string.service_sports_equipment_repair)),
            ServiceEntity(id = 184, categoryId = 12, name = stringResource(Res.string.service_other_service)),
            ServiceEntity(id = 185, categoryId = 12, name = stringResource(Res.string.service_emergency_opening_locks))
        )
    )
)
}

private val rowShape = RoundedCornerShape(dimens_16)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceSelectionSheet(
    visible: Boolean,
    selectedCategory: String?,
    selectedService: String?,
    onDismissRequest: () -> Unit,
    onServiceSelected: (category: String, service: String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expandedCategory by remember { mutableStateOf(selectedCategory) }
    val serviceCategories = getServiceCategories()

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.10f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDismissRequest() }
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 20.dp)
                    .clickable(
                        indication = null,
                        enabled = false,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { }
            ) {
                val sheetMaxHeight = (maxHeight - 88.dp).coerceAtLeast(220.dp)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Surface(
                        shape = SheetShape,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .shadow(dimens_1, SheetShape)
                            .fillMaxWidth()
                            .heightIn(max = sheetMaxHeight)
                    ) {
                        Column(modifier = Modifier.padding(top = dimens_8, bottom = dimens_8)) {
                            DealSpotTextInputField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 4.dp),
                                leftIcon = Res.drawable.ic_search,
                                placeHolderText = stringResource(Res.string.search_for_service),
                                isPasswordField = false,
                                imeAction = ImeAction.Done,
                                labelTextColor = Grey
                            ) { service ->
                                println("ServiceSelectionSheet. Service is: $service")

                                query = service
                            }

                            val filteredResults = remember(query) {
                                if (query.isBlank()) emptyList()
                                else serviceCategories.flatMap { category ->
                                    category.services.filter { it.name.contains(query, ignoreCase = true) }
                                        .map { service -> category to service }
                                }
                            }

                            if (filteredResults.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    items(filteredResults) { (category, service) ->
                                        ServiceResultRow(
                                            category = category,
                                            service = service,
                                            isSelected = selectedService == service.name,
                                            onClick = {
                                                query = ""
                                                expandedCategory = category.name
                                                onServiceSelected(category.name, service.name)
                                            }
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    items(serviceCategories) { category ->
                                        CategoryRow(
                                            category = category,
                                            isExpanded = expandedCategory == category.name,
                                            isSelected = selectedCategory == category.name,
                                            onClick = {
                                                expandedCategory = if (expandedCategory == category.name) null else category.name
                                            }
                                        )

                                        AnimatedVisibility(visible = expandedCategory == category.name) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                            ) {
                                                FlowRow(
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    category.services.forEach { service ->
                                                        val selected = selectedService == service.name
                                                        val borderStroke = BorderStroke(
                                                            width = 1.dp,
                                                            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                                        )
                                                        AssistChip(
                                                            onClick = {
                                                                query = ""
                                                                onServiceSelected(category.name, service.name)
                                                            },
                                                            label = { Text(service.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                                            colors = AssistChipDefaults.assistChipColors(
                                                                containerColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                                                                labelColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                                            ),
                                                            border = borderStroke
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }
                        }
                    }

                    SpacerHeight12Dp()

                    DealSpotOutlineButton(
                        modifier = Modifier.width(dimens_200),
                        buttonText = stringResource(Res.string.cancel),
                        buttonHeight = dimens_45,
                        fillWidth = false,
                        shape = RoundedCornerShape(18.dp),
                        containerColor = white,
                        borderColor = grey_middle
                    ) {
                        onDismissRequest()
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(
    category: ServiceCategoryEntity,
    isExpanded: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    Surface(
        onClick = onClick,
        shape = rowShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp),
                    tint = DealSpotDark
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = latoFontFamily()),
                    fontWeight = FontWeight.W600,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (isExpanded) {
                    Text(
                        text = stringResource(Res.string.select_service),
                        style = MaterialTheme.typography.bodySmall.copy(fontFamily = latoFontFamily()),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = if (isExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ServiceResultRow(
    category: ServiceCategoryEntity,
    service: ServiceEntity,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .padding(6.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        SpacerWidth15Dp()

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = service.name,
                style = MaterialTheme.typography.titleMedium.copy(fontFamily = latoFontFamily()),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodySmall.copy(fontFamily = latoFontFamily()),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ServiceSelectionField(
    selectedCategory: String?,
    selectedService: String?,
    onClick: () -> Unit
) {
    val borderColor = if (selectedService != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
    Surface(
        onClick = onClick,
        shape = rowShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            if (selectedService != null) {
                Text(
                    text = selectedService,
                    fontWeight = FontWeight.W600,
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = latoFontFamily())
                )
                selectedCategory?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Text(
                    text = stringResource(Res.string.tap_to_choose_service),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
