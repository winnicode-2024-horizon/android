package id.winnicode.horizon.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem (
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: Screen
)