package com.example.iwanttobelieve.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

// Classe que representa um item da barra de navegação inferior
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
