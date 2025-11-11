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
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.School
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.DealSpotDark
import com.app.dealspot.presentation.theme.Grey
import com.app.dealspot.presentation.theme.SpacerHeight12Dp
import com.app.dealspot.presentation.theme.dimens_20
import com.app.dealspot.presentation.theme.dimens_200
import com.app.dealspot.presentation.theme.dimens_45
import com.app.dealspot.presentation.theme.dimens_8
import com.app.dealspot.presentation.theme.latoFontFamily
import com.app.dealspot.presentation.theme.white
import com.app.dealspot.presentation.view.DealSpotOutlineButton
import com.app.dealspot.presentation.view.DealSpotTextInputField
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_search
import androidx.compose.ui.text.input.ImeAction
import com.app.dealspot.presentation.theme.SpacerWidth15Dp
import com.app.dealspot.presentation.theme.dimens_1
import com.app.dealspot.presentation.theme.grey_middle

private val SheetShape = RoundedCornerShape(dimens_20)

private data class ServiceCategory(
    val name: String,
    val icon: ImageVector,
    val services: List<String>
)

private val serviceCategories = listOf(
    ServiceCategory(
        name = "Skilled Trades & Technical Services",
        icon = Icons.Outlined.Construction,
        services = listOf(
            "Electrician", "Plumber", "Carpenter", "Welder",
            "Painter", "Locksmith", "Solar Panel", "Handyman",
            "Heating, Ventilation & Air Conditioning", "Gas Fitter", "Refrigeration Mechanic",
            "Boiler Technician", "Water Treatment Installer", "Fire Protection Installer",
            "Elevator installer", "Smart Home Technician", "Security System Installer", "Network Installer",
            "Wind Renewable Energy Technician", "Woodcarver", "Paving Installer (Asphalt, Concrete, Pavers)", "Lighting",
            "Household appliance repair"
        )
    ),
    ServiceCategory(
        name = "Building & Cleaning Services",
        icon = Icons.Outlined.CleaningServices,
        services = listOf(
            "Janitor", "Housekeeper", "Hotel Cleaner", "Window Cleaner", "Pest Control Technician",
            "Waste Collector", "Landscaper", "Gardener", "Tree Surgeon", "Lawn Care Specialist",
            "Pool Maintenance Technician", "Snow Removal", "Interior Designer", "Interior Decorator",
            "Bricklayer / Mason", "Rebar Installer (Steel Fixer)", "Formwork Carpenter", "Construction Worker",
            "Framer (Wood or Metal Stud)", "Drywaller", "Roofer", "Tiler (Roof, Floor, or Wall)", "Demolition Worker",
            "Plasterer", "Wallpaper Installer", "Floor Layer (Vinyl, Laminate, Hardwood)", "Ceiling Installer (Drop, Stretch)",
            "Window Installer", "Door Installer", "Glazier (Glass Installer)", "Joiner", "Kitchen Fitter",
            "Bathroom Fitter", "Countertop Fabricator (Stone / Quartz)", "Stone Mason", "Stair Installer",
            "Fence Installer", "Irrigation System Installer", "Epoxy Floor Installer"
        )
    ),
    ServiceCategory(
        name = "Health & Human Services",
        icon = Icons.Outlined.HealthAndSafety,
        services = listOf(
            "Doctor", "Nurse", "Physical Therapist", "Speech Therapist", "Psychologist", "Psychiatrist",
            "Housekeeping assistant", "Nutritionist / Dietitian", "Fitness Trainer", "Massage Therapist", "Veterinarian"
        )
    ),

    // TODO() ЗУПИНИВСЯ ТУТ
    ServiceCategory(
        name = "Personal & Lifestyle Services",
        icon = Icons.Outlined.Person,
        services = listOf(
            "Barber", "Hair Stylist", "Makeup Artist", "Nail Technician", "Esthetician", "Tattoo Artist",
            "Personal Shopper", "Tailor / Seamstress", "Personal Trainer", "Life Coach", "Dog Groomer", "Concierge"
        )
    ),
    ServiceCategory(
        name = "Food & Hospitality Services",
        icon = Icons.Outlined.Restaurant,
        services = listOf(
            "Chef", "Cook", "Baker", "Butcher", "Waiter / Waitress", "Bartender", "Barista", "Caterer", "Restaurant Manager",
            "Hotel Receptionist", "Event Planner", "Wedding Planner", "Travel Agent", "Tour Guide"
        )
    ),
    ServiceCategory(
        name = "Business, Finance & Administrative Services",
        icon = Icons.Outlined.AccountBalance,
        services = listOf(
            "Accountant", "Bookkeeper", "Financial Advisor", "Banker", "Insurance Agent", "Real Estate Agent",
            "Property Manager", "Administrative Assistant", "Customer Service Representative", "HR Specialist",
            "Recruiter", "Tax Preparer"
        )
    ),
    ServiceCategory(
        name = "Legal & Protective Services",
        icon = Icons.Outlined.Gavel,
        services = listOf(
            "Lawyer", "Paralegal", "Notary Public", "Court Clerk", "Police Officer", "Detective", "Security Guard",
            "Correctional Officer", "Firefighter", "Border Patrol Officer", "Military Service Member"
        )
    ),
    ServiceCategory(
        name = "Technology & Information Services",
        icon = Icons.Outlined.Computer,
        services = listOf(
            "IT Support Specialist", "Network Administrator", "Cybersecurity Specialist", "Software Engineer",
            "Web Developer", "Technical Support Agent", "Computer Repair Technician", "Data Analyst", "Drone Operator"
        )
    ),
    ServiceCategory(
        name = "Auto Services",
        icon = Icons.Outlined.DirectionsCar,
        services = listOf("Auto mechanic", "Auto electrician", "Other")
    ),
    ServiceCategory(
        name = "Creative, Cultural & Media Services",
        icon = Icons.Outlined.Brush,
        services = listOf(
            "Photographer", "Videographer", "Graphic Designer", "Illustrator", "Animator", "Interior Designer",
            "Fashion Designer", "Actor", "Musician", "Sound Engineer", "Writer", "Journalist", "Translator"
        )
    ),
    ServiceCategory(
        name = "Environmental, Agricultural & Outdoor Services",
        icon = Icons.Outlined.Eco,
        services = listOf(
            "Farmer", "Rancher", "Beekeeper", "Fisherman", "Forester", "Park Ranger", "Wildlife Biologist",
            "Zookeeper", "Horticulturist", "Conservation Officer", "Sustainability Consultant"
        )
    ),
    ServiceCategory(
        name = "Funeral & Memorial Services",
        icon = Icons.Outlined.FavoriteBorder,
        services = listOf(
            "Funeral Director", "Mortician", "Embalmer", "Crematorium Operator", "Bereavement Counselor"
        )
    ),
    ServiceCategory(
        name = "Other Services",
        icon = Icons.Outlined.DevicesOther,
        services = listOf(
            "Walking pets", "Take out the trash"
        )
    )
)

private val rowShape = RoundedCornerShape(16.dp)

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
                                placeHolderText = "Search for a service",
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
                                    category.services.filter { it.contains(query, ignoreCase = true) }
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
                                            isSelected = selectedService == service,
                                            onClick = {
                                                query = ""
                                                expandedCategory = category.name
                                                onServiceSelected(category.name, service)
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
                                                        val selected = selectedService == service
                                                        val borderStroke = BorderStroke(
                                                            width = 1.dp,
                                                            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                                        )
                                                        AssistChip(
                                                            onClick = {
                                                                query = ""
                                                                onServiceSelected(category.name, service)
                                                            },
                                                            label = { Text(service, maxLines = 1, overflow = TextOverflow.Ellipsis) },
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
                        buttonText = "Cancel",
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
    category: ServiceCategory,
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
                        text = "Select a service",
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
    category: ServiceCategory,
    service: String,
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
                text = service,
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
                    text = "Tap to choose a service",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
