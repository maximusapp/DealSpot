package com.app.dealspot.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.dealspot.presentation.theme.SpacerWidth15Dp
import dealspot.composeapp.generated.resources.Res
import dealspot.composeapp.generated.resources.ic_menu_chat
import dealspot.composeapp.generated.resources.ic_menu_profile
import dealspot.composeapp.generated.resources.ic_menu_settings
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    homeSelected: Boolean,
    settingsSelected: Boolean,
    chatsSelected: Boolean,
    onHomeClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onChatsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onPlusClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            val homeColor = if (homeSelected) MaterialTheme.colorScheme.primary else Color.Gray
//            Icon(
//                imageVector = if (homeSelected) Icons.Filled.Home else Icons.Outlined.Home,
//                contentDescription = "Home",
//                tint = homeColor,
//                modifier = Modifier
//                    .size(28.dp)
//                    .clickable { onHomeClick() }
//            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { onChatsClick() },
                contentAlignment = Alignment.Center
            ) {
                val chatsColor = if (chatsSelected) MaterialTheme.colorScheme.primary else Color.Gray
                Icon(
                    painter = painterResource(Res.drawable.ic_menu_chat),
                    contentDescription = "Chats",
                    tint = chatsColor,
                    modifier = Modifier
                        .size(30.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { onSettingsClick() },
                contentAlignment = Alignment.Center
            ) {
                val settingsColor = if (settingsSelected) MaterialTheme.colorScheme.primary else Color.Gray
                Icon(
                    painter = painterResource(Res.drawable.ic_menu_settings),
                    contentDescription = "Settings",
                    tint = settingsColor,
                    modifier = Modifier
                        .size(30.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                val settingsColor = if (settingsSelected) MaterialTheme.colorScheme.primary else Color.Gray
                Icon(
                    painter = painterResource(Res.drawable.ic_menu_profile),
                    contentDescription = "Profile",
                    tint = settingsColor,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        SpacerWidth15Dp()

        FloatingActionButton(
            modifier = Modifier.size(50.dp),
            onClick = onPlusClick,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
            containerColor = MaterialTheme.colorScheme.onSurface,
            shape = androidx.compose.foundation.shape.CircleShape
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.surface)
        }
    }
}
