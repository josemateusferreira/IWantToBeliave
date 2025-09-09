package com.example.iwanttobelieve.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iwanttobelieve.ui.feed.FeedScreen
import com.example.iwanttobelieve.ui.post.NewPostScreen
import com.example.iwanttobelieve.ui.profile.ProfileScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem(Destinations.FEED, "Feed", Icons.Filled.Home),
        BottomNavItem(Destinations.NEW_POST, "Novo", Icons.Filled.Add),
        BottomNavItem(Destinations.PROFILE, "Perfil", Icons.Filled.Person)
    )

    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF121212)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (currentRoute == item.route) Color(0xFF00FFFF) else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                item.label,
                                color = if (currentRoute == item.route) Color(0xFF00FFFF) else Color.Gray
                            )
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.FEED,
            modifier = Modifier
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF000000), Color(0xFF1A1A2E))
                    )
                )
        ) {
            composable(Destinations.FEED) { FeedScreen() }
            composable(Destinations.NEW_POST) { NewPostScreen(navController) }
            composable(Destinations.PROFILE) { ProfileScreen() }
        }
    }
}
