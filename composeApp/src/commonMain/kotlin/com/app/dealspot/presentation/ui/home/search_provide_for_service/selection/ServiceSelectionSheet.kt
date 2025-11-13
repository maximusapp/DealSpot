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
            ServiceEntity(id = 1000, categoryId = 1, name = stringResource(Res.string.service_all_home_appliance_repair_services)),
            ServiceEntity(id = 1002, categoryId = 1, name = stringResource(Res.string.service_deep_fryer_repair)),
            ServiceEntity(id = 1003, categoryId = 1, name = stringResource(Res.string.service_kitchen_hood_repair)),
            ServiceEntity(id = 1004, categoryId = 1, name = stringResource(Res.string.service_grill_repair)),
            ServiceEntity(id = 1005, categoryId = 1, name = stringResource(Res.string.service_water_cooler_cleaning)),
            ServiceEntity(id = 1006, categoryId = 1, name = stringResource(Res.string.service_air_conditioner_cleaning)),
            ServiceEntity(id = 1007, categoryId = 1, name = stringResource(Res.string.service_water_heater_boiler_cleaning)),
            ServiceEntity(id = 1008, categoryId = 1, name = stringResource(Res.string.service_sewing_machine_repair)),
            ServiceEntity(id = 1009, categoryId = 1, name = stringResource(Res.string.service_refrigeration_equipment_display_case_repair)),
            ServiceEntity(id = 1010, categoryId = 1, name = stringResource(Res.string.service_refrigerator_repair)),
            ServiceEntity(id = 1011, categoryId = 1, name = stringResource(Res.string.service_bread_maker_repair)),
            ServiceEntity(id = 1012, categoryId = 1, name = stringResource(Res.string.service_camera_repair)),
            ServiceEntity(id = 1013, categoryId = 1, name = stringResource(Res.string.service_hair_dryer_repair)),
            ServiceEntity(id = 1014, categoryId = 1, name = stringResource(Res.string.service_dough_mixer_repair)),
            ServiceEntity(id = 1016, categoryId = 1, name = stringResource(Res.string.service_telephone_repair)),
            ServiceEntity(id = 1017, categoryId = 1, name = stringResource(Res.string.service_television_repair)),
            ServiceEntity(id = 1018, categoryId = 1, name = stringResource(Res.string.service_dryer_repair)),
            ServiceEntity(id = 1020, categoryId = 1, name = stringResource(Res.string.service_pressure_cooker_repair)),
            ServiceEntity(id = 1021, categoryId = 1, name = stringResource(Res.string.service_tv_remote_control_repair)),
            ServiceEntity(id = 1022, categoryId = 1, name = stringResource(Res.string.service_projector_repair)),
            ServiceEntity(id = 1023, categoryId = 1, name = stringResource(Res.string.service_iron_repair)),
            ServiceEntity(id = 1024, categoryId = 1, name = stringResource(Res.string.service_washing_machine_repair)),
            ServiceEntity(id = 1025, categoryId = 1, name = stringResource(Res.string.service_dishwasher_repair)),
            ServiceEntity(id = 1026, categoryId = 1, name = stringResource(Res.string.service_vacuum_cleaner_repair)),
            ServiceEntity(id = 1027, categoryId = 1, name = stringResource(Res.string.service_overlock_machine_repair)),
            ServiceEntity(id = 1028, categoryId = 1, name = stringResource(Res.string.service_instant_water_heater_repair)),
            ServiceEntity(id = 1029, categoryId = 1, name = stringResource(Res.string.service_multicooker_repair)),
            ServiceEntity(id = 1030, categoryId = 1, name = stringResource(Res.string.service_music_center_repair)),
            ServiceEntity(id = 1031, categoryId = 1, name = stringResource(Res.string.service_freezer_repair)),
            ServiceEntity(id = 1032, categoryId = 1, name = stringResource(Res.string.service_microwave_oven_repair)),
            ServiceEntity(id = 1033, categoryId = 1, name = stringResource(Res.string.service_ice_maker_repair)),
            ServiceEntity(id = 1034, categoryId = 1, name = stringResource(Res.string.service_kitchen_food_processor_repair)),
            ServiceEntity(id = 1035, categoryId = 1, name = stringResource(Res.string.service_water_cooler_repair)),
            ServiceEntity(id = 1036, categoryId = 1, name = stringResource(Res.string.service_air_conditioner_repair)),
            ServiceEntity(id = 1037, categoryId = 1, name = stringResource(Res.string.service_space_heater_repair)),
            ServiceEntity(id = 1038, categoryId = 1, name = stringResource(Res.string.service_coffee_maker_repair)),
            ServiceEntity(id = 1039, categoryId = 1, name = stringResource(Res.string.service_induction_cooktop_repair)),
            ServiceEntity(id = 1040, categoryId = 1, name = stringResource(Res.string.service_electric_stove_repair)),
            ServiceEntity(id = 1041, categoryId = 1, name = stringResource(Res.string.service_electric_cooktop_repair)),
            ServiceEntity(id = 1042, categoryId = 1, name = stringResource(Res.string.service_electric_meat_grinder_repair)),
            ServiceEntity(id = 1043, categoryId = 1, name = stringResource(Res.string.service_electric_fireplace_repair)),
            ServiceEntity(id = 1044, categoryId = 1, name = stringResource(Res.string.service_oven_repair)),
            ServiceEntity(id = 1045, categoryId = 1, name = stringResource(Res.string.service_home_audio_system_repair)),
            ServiceEntity(id = 1046, categoryId = 1, name = stringResource(Res.string.service_home_theater_repair)),
            ServiceEntity(id = 1047, categoryId = 1, name = stringResource(Res.string.service_cooktop_repair)),
            ServiceEntity(id = 1048, categoryId = 1, name = stringResource(Res.string.service_boiler_repair)),
            ServiceEntity(id = 1049, categoryId = 1, name = stringResource(Res.string.service_blender_repair)),
            ServiceEntity(id = 1050, categoryId = 1, name = stringResource(Res.string.service_antenna_repair)),
            ServiceEntity(id = 1051, categoryId = 1, name = stringResource(Res.string.service_tuner_firmware_update)),
            ServiceEntity(id = 1052, categoryId = 1, name = stringResource(Res.string.service_television_dismantling)),
            ServiceEntity(id = 1053, categoryId = 1, name = stringResource(Res.string.service_dryer_dismantling)),
            ServiceEntity(id = 1054, categoryId = 1, name = stringResource(Res.string.service_washing_machine_dismantling)),
            ServiceEntity(id = 1055, categoryId = 1, name = stringResource(Res.string.service_dishwasher_dismantling)),
            ServiceEntity(id = 1056, categoryId = 1, name = stringResource(Res.string.service_air_conditioner_dismantling)),
            ServiceEntity(id = 1057, categoryId = 1, name = stringResource(Res.string.service_cooktop_dismantling)),
            ServiceEntity(id = 1058, categoryId = 1, name = stringResource(Res.string.service_boiler_dismantling)),
            ServiceEntity(id = 1059, categoryId = 1, name = stringResource(Res.string.service_other))
        )
    ),
    // 2. Skilled Trades & Technical Services - Electricians, plumbers, handymen (very common)
    ServiceCategoryEntity(
        id = 2,
        name = stringResource(Res.string.category_skilled_trades_technical_services),
        icon = Icons.Outlined.Construction,
        services = listOf(
            ServiceEntity(id = 2000, categoryId = 2, name = stringResource(Res.string.service_electrician)),
            ServiceEntity(id = 2001, categoryId = 2, name = stringResource(Res.string.service_plumber)),
            ServiceEntity(id = 2002, categoryId = 2, name = stringResource(Res.string.service_carpenter)),
            ServiceEntity(id = 2003, categoryId = 2, name = stringResource(Res.string.service_welder)),
            ServiceEntity(id = 2004, categoryId = 2, name = stringResource(Res.string.service_painter)),
            ServiceEntity(id = 2005, categoryId = 2, name = stringResource(Res.string.service_locksmith)),
            ServiceEntity(id = 2006, categoryId = 2, name = stringResource(Res.string.service_solar_panel)),
            ServiceEntity(id = 2007, categoryId = 2, name = stringResource(Res.string.service_handyman)),
            ServiceEntity(id = 2008, categoryId = 2, name = stringResource(Res.string.service_heating_ventilation_air_conditioning)),
            ServiceEntity(id = 2009, categoryId = 2, name = stringResource(Res.string.service_gas_fitter)),
            ServiceEntity(id = 2010, categoryId = 2, name = stringResource(Res.string.service_refrigeration_mechanic)),
            ServiceEntity(id = 2011, categoryId = 2, name = stringResource(Res.string.service_boiler_technician)),
            ServiceEntity(id = 2012, categoryId = 2, name = stringResource(Res.string.service_water_treatment_installer)),
            ServiceEntity(id = 2013, categoryId = 2, name = stringResource(Res.string.service_fire_protection_installer)),
            ServiceEntity(id = 2014, categoryId = 2, name = stringResource(Res.string.service_elevator_specialist)),
            ServiceEntity(id = 2015, categoryId = 2, name = stringResource(Res.string.service_smart_home_technician)),
            ServiceEntity(id = 2016, categoryId = 2, name = stringResource(Res.string.service_security_system_specialist)),
            ServiceEntity(id = 2017, categoryId = 2, name = stringResource(Res.string.service_network_installer)),
            ServiceEntity(id = 2018, categoryId = 2, name = stringResource(Res.string.service_wind_renewable_energy_technician)),
            ServiceEntity(id = 2019, categoryId = 2, name = stringResource(Res.string.service_woodcarver)),
            ServiceEntity(id = 2020, categoryId = 2, name = stringResource(Res.string.service_road_surface_installer)),
            ServiceEntity(id = 2021, categoryId = 2, name = stringResource(Res.string.service_lighting_specialist)),
            ServiceEntity(id = 2022, categoryId = 2, name = stringResource(Res.string.service_other))
        )
    ),
    // 3. Auto Services - Car repairs are very common
    ServiceCategoryEntity(
        id = 3,
        name = stringResource(Res.string.category_auto_services),
        icon = Icons.Outlined.DirectionsCar,
        services = listOf(
            ServiceEntity(id = 3000, categoryId = 3, name = stringResource(Res.string.service_auto_mechanic)),
            ServiceEntity(id = 3001, categoryId = 3, name = stringResource(Res.string.service_auto_electrician)),
            ServiceEntity(id = 3002, categoryId = 3, name = stringResource(Res.string.service_other))
        )
    ),
    // 4. Building & Cleaning Services - Cleaning, maintenance, construction
    ServiceCategoryEntity(
        id = 4,
        name = stringResource(Res.string.category_building_cleaning_services),
        icon = Icons.Outlined.CleaningServices,
        services = listOf(
            ServiceEntity(id = 4000, categoryId = 4, name = stringResource(Res.string.service_janitor)),
            ServiceEntity(id = 4001, categoryId = 4, name = stringResource(Res.string.service_housekeeper)),
            ServiceEntity(id = 4002, categoryId = 4, name = stringResource(Res.string.service_hotel_cleaner)),
            ServiceEntity(id = 4003, categoryId = 4, name = stringResource(Res.string.service_window_cleaner)),
            ServiceEntity(id = 4004, categoryId = 4, name = stringResource(Res.string.service_pest_control_technician)),
            ServiceEntity(id = 4005, categoryId = 4, name = stringResource(Res.string.service_waste_collector)),
            ServiceEntity(id = 4006, categoryId = 4, name = stringResource(Res.string.service_landscaper)),
            ServiceEntity(id = 4007, categoryId = 4, name = stringResource(Res.string.service_gardener)),
            ServiceEntity(id = 4008, categoryId = 4, name = stringResource(Res.string.service_tree_surgeon)),
            ServiceEntity(id = 4009, categoryId = 4, name = stringResource(Res.string.service_lawn_care_specialist)),
            ServiceEntity(id = 4010, categoryId = 4, name = stringResource(Res.string.service_pool_maintenance_technician)),
            ServiceEntity(id = 4011, categoryId = 4, name = stringResource(Res.string.service_snow_removal)),
            ServiceEntity(id = 4012, categoryId = 4, name = stringResource(Res.string.service_interior_designer)),
            ServiceEntity(id = 4013, categoryId = 4, name = stringResource(Res.string.service_interior_decorator)),
            ServiceEntity(id = 4014, categoryId = 4, name = stringResource(Res.string.service_bricklayer_mason)),
            ServiceEntity(id = 4015, categoryId = 4, name = stringResource(Res.string.service_rebar_installer)),
            ServiceEntity(id = 4016, categoryId = 4, name = stringResource(Res.string.service_construction_worker)),
            ServiceEntity(id = 4017, categoryId = 4, name = stringResource(Res.string.service_framer)),
            ServiceEntity(id = 4018, categoryId = 4, name = stringResource(Res.string.service_drywaller)),
            ServiceEntity(id = 4019, categoryId = 4, name = stringResource(Res.string.service_roofer)),
            ServiceEntity(id = 4020, categoryId = 4, name = stringResource(Res.string.service_tiler)),
            ServiceEntity(id = 4021, categoryId = 4, name = stringResource(Res.string.service_demolition_worker)),
            ServiceEntity(id = 4022, categoryId = 4, name = stringResource(Res.string.service_plasterer)),
            ServiceEntity(id = 4023, categoryId = 4, name = stringResource(Res.string.service_wallpaper_installer)),
            ServiceEntity(id = 4024, categoryId = 4, name = stringResource(Res.string.service_floor_layer)),
            ServiceEntity(id = 4025, categoryId = 4, name = stringResource(Res.string.service_ceiling_installer)),
            ServiceEntity(id = 4026, categoryId = 4, name = stringResource(Res.string.service_window_installer)),
            ServiceEntity(id = 4027, categoryId = 4, name = stringResource(Res.string.service_door_installer)),
            ServiceEntity(id = 4028, categoryId = 4, name = stringResource(Res.string.service_glazier)),
            ServiceEntity(id = 4029, categoryId = 4, name = stringResource(Res.string.service_joiner)),
            ServiceEntity(id = 4030, categoryId = 4, name = stringResource(Res.string.service_kitchen_fitter)),
            ServiceEntity(id = 4031, categoryId = 4, name = stringResource(Res.string.service_bathroom_fitter)),
            ServiceEntity(id = 4032, categoryId = 4, name = stringResource(Res.string.service_countertop_fabricator)),
            ServiceEntity(id = 4033, categoryId = 4, name = stringResource(Res.string.service_stone_mason)),
            ServiceEntity(id = 4034, categoryId = 4, name = stringResource(Res.string.service_stair_installer)),
            ServiceEntity(id = 4035, categoryId = 4, name = stringResource(Res.string.service_fence_installer)),
            ServiceEntity(id = 4036, categoryId = 4, name = stringResource(Res.string.service_irrigation_system_installer)),
            ServiceEntity(id = 4037, categoryId = 4, name = stringResource(Res.string.service_epoxy_floor_installer)),
            ServiceEntity(id = 4038, categoryId = 4, name = stringResource(Res.string.service_upholstered_furniture_carpet_cleaning)),
            ServiceEntity(id = 4039, categoryId = 4, name = stringResource(Res.string.service_other))
        )
    ),
    // 5. Technology & Information Services - IT support, computer repairs
    ServiceCategoryEntity(
        id = 5,
        name = stringResource(Res.string.category_technology_information_services),
        icon = Icons.Outlined.Computer,
        services = listOf(
            ServiceEntity(id = 5000, categoryId = 5, name = stringResource(Res.string.service_network_administrator)),
            ServiceEntity(id = 5001, categoryId = 5, name = stringResource(Res.string.service_cybersecurity_specialist)),
            ServiceEntity(id = 5002, categoryId = 5, name = stringResource(Res.string.service_software_engineer)),
            ServiceEntity(id = 5003, categoryId = 5, name = stringResource(Res.string.service_repair_maintenance_computer_equipment)),
            ServiceEntity(id = 5004, categoryId = 5, name = stringResource(Res.string.service_other))
        )
    ),
    // 6. Personal & Lifestyle Services - Haircuts, personal care
    ServiceCategoryEntity(
        id = 6,
        name = stringResource(Res.string.category_personal_lifestyle_services),
        icon = Icons.Outlined.Person,
        services = listOf(
            ServiceEntity(id = 6000, categoryId = 6, name = stringResource(Res.string.service_barber)),
            ServiceEntity(id = 6001, categoryId = 6, name = stringResource(Res.string.service_hair_stylist)),
            ServiceEntity(id = 6002, categoryId = 6, name = stringResource(Res.string.service_makeup_artist)),
            ServiceEntity(id = 6003, categoryId = 6, name = stringResource(Res.string.service_nail_technician)),
            ServiceEntity(id = 6004, categoryId = 6, name = stringResource(Res.string.service_cosmetologist)),
            ServiceEntity(id = 6005, categoryId = 6, name = stringResource(Res.string.service_tattoo_artist)),
            ServiceEntity(id = 6006, categoryId = 6, name = stringResource(Res.string.service_seamstress)),
            ServiceEntity(id = 6007, categoryId = 6, name = stringResource(Res.string.service_personal_trainer)),
            ServiceEntity(id = 6008, categoryId = 6, name = stringResource(Res.string.service_other))
        )
    ),
    // 7. Health & Human Services - Medical services
    ServiceCategoryEntity(
        id = 7,
        name = stringResource(Res.string.category_health_human_services),
        icon = Icons.Outlined.HealthAndSafety,
        services = listOf(
            ServiceEntity(id = 7000, categoryId = 7, name = stringResource(Res.string.service_doctor)),
            ServiceEntity(id = 7001, categoryId = 7, name = stringResource(Res.string.service_nurse)),
            ServiceEntity(id = 7002, categoryId = 7, name = stringResource(Res.string.service_physical_therapist)),
            ServiceEntity(id = 7003, categoryId = 7, name = stringResource(Res.string.service_speech_therapist)),
            ServiceEntity(id = 7004, categoryId = 7, name = stringResource(Res.string.service_psychologist)),
            ServiceEntity(id = 7005, categoryId = 7, name = stringResource(Res.string.service_psychiatrist)),
            ServiceEntity(id = 7006, categoryId = 7, name = stringResource(Res.string.service_housekeeping_assistant)),
            ServiceEntity(id = 7007, categoryId = 7, name = stringResource(Res.string.service_nutritionist_dietitian)),
            ServiceEntity(id = 7008, categoryId = 7, name = stringResource(Res.string.service_fitness_trainer)),
            ServiceEntity(id = 7009, categoryId = 7, name = stringResource(Res.string.service_massage_therapist)),
            ServiceEntity(id = 7010, categoryId = 7, name = stringResource(Res.string.service_veterinarian)),
            ServiceEntity(id = 7011, categoryId = 7, name = stringResource(Res.string.service_other))
        )
    ),
    // 8. Food & Hospitality Services
    ServiceCategoryEntity(
        id = 8,
        name = stringResource(Res.string.category_food_hospitality_services),
        icon = Icons.Outlined.Restaurant,
        services = listOf(
            ServiceEntity(id = 8000, categoryId = 8, name = stringResource(Res.string.service_chef)),
            ServiceEntity(id = 8001, categoryId = 8, name = stringResource(Res.string.service_cook)),
            ServiceEntity(id = 8002, categoryId = 8, name = stringResource(Res.string.service_baker)),
            ServiceEntity(id = 8003, categoryId = 8, name = stringResource(Res.string.service_butcher)),
            ServiceEntity(id = 8004, categoryId = 8, name = stringResource(Res.string.service_waiter_waitress)),
            ServiceEntity(id = 8005, categoryId = 8, name = stringResource(Res.string.service_bartender)),
            ServiceEntity(id = 8006, categoryId = 8, name = stringResource(Res.string.service_barista)),
            ServiceEntity(id = 8007, categoryId = 8, name = stringResource(Res.string.service_event_planner)),
            ServiceEntity(id = 8008, categoryId = 8, name = stringResource(Res.string.service_wedding_planner)),
            ServiceEntity(id = 8009, categoryId = 8, name = stringResource(Res.string.service_tour_guide)),
            ServiceEntity(id = 8010, categoryId = 8, name = stringResource(Res.string.service_other))
        )
    ),
    // 9. Business, Finance & Administrative Services
    ServiceCategoryEntity(
        id = 9,
        name = stringResource(Res.string.category_business_finance_administrative_services),
        icon = Icons.Outlined.AccountBalance,
        services = listOf(
            ServiceEntity(id = 9000, categoryId = 9, name = stringResource(Res.string.service_accountant)),
            ServiceEntity(id = 9001, categoryId = 9, name = stringResource(Res.string.service_financial_advisor)),
            ServiceEntity(id = 9002, categoryId = 9, name = stringResource(Res.string.service_real_estate_agent)),
            ServiceEntity(id = 9003, categoryId = 9, name = stringResource(Res.string.service_other))
        )
    ),
    // 10. Legal & Protective Services
    ServiceCategoryEntity(
        id = 10,
        name = stringResource(Res.string.category_legal_protective_services),
        icon = Icons.Outlined.Gavel,
        services = listOf(
            ServiceEntity(id = 10000, categoryId = 10, name = stringResource(Res.string.service_lawyer)),
            ServiceEntity(id = 10001, categoryId = 10, name = stringResource(Res.string.service_paralegal)),
            ServiceEntity(id = 10002, categoryId = 10, name = stringResource(Res.string.service_notary_public)),
            ServiceEntity(id = 10003, categoryId = 10, name = stringResource(Res.string.service_security_guard)),
            ServiceEntity(id = 10004, categoryId = 10, name = stringResource(Res.string.service_other))
        )
    ),
    // 11. Creative, Cultural & Media Services
    ServiceCategoryEntity(
        id = 11,
        name = stringResource(Res.string.category_creative_cultural_media_services),
        icon = Icons.Outlined.Brush,
        services = listOf(
            ServiceEntity(id = 11000, categoryId = 11, name = stringResource(Res.string.service_photographer)),
            ServiceEntity(id = 11001, categoryId = 11, name = stringResource(Res.string.service_videographer)),
            ServiceEntity(id = 11002, categoryId = 11, name = stringResource(Res.string.service_graphic_designer)),
            ServiceEntity(id = 11003, categoryId = 11, name = stringResource(Res.string.service_illustrator)),
            ServiceEntity(id = 11004, categoryId = 11, name = stringResource(Res.string.service_animator)),
            ServiceEntity(id = 11005, categoryId = 11, name = stringResource(Res.string.service_actor)),
            ServiceEntity(id = 11006, categoryId = 11, name = stringResource(Res.string.service_musician)),
            ServiceEntity(id = 11007, categoryId = 11, name = stringResource(Res.string.service_sound_engineer)),
            ServiceEntity(id = 11008, categoryId = 11, name = stringResource(Res.string.service_writer)),
            ServiceEntity(id = 11009, categoryId = 11, name = stringResource(Res.string.service_journalist)),
            ServiceEntity(id = 11010, categoryId = 11, name = stringResource(Res.string.service_translator)),
            ServiceEntity(id = 11011, categoryId = 11, name = stringResource(Res.string.service_other))
        )
    ),
    // 12. Other Services - Catch-all category (last)
    ServiceCategoryEntity(
        id = 12,
        name = stringResource(Res.string.category_other_services),
        icon = Icons.Outlined.DevicesOther,
        services = listOf(
            ServiceEntity(id = 12000, categoryId = 12, name = stringResource(Res.string.service_walking_pets)),
            ServiceEntity(id = 12001, categoryId = 12, name = stringResource(Res.string.service_take_out_trash)),
            ServiceEntity(id = 12002, categoryId = 12, name = stringResource(Res.string.service_loader_services)),
            ServiceEntity(id = 12003, categoryId = 12, name = stringResource(Res.string.service_cargo_transportation_services)),
            ServiceEntity(id = 12004, categoryId = 12, name = stringResource(Res.string.service_furniture_reupholstery_restoration)),
            ServiceEntity(id = 12005, categoryId = 12, name = stringResource(Res.string.service_sports_equipment_repair)),
            ServiceEntity(id = 12006, categoryId = 12, name = stringResource(Res.string.service_other_service)),
            ServiceEntity(id = 12007, categoryId = 12, name = stringResource(Res.string.service_emergency_opening_locks)),
            ServiceEntity(id = 12008, categoryId = 12, name = stringResource(Res.string.service_other))
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
