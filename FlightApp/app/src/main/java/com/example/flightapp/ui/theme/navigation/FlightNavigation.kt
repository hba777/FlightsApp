package com.example.flightapp.ui.theme.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flightapp.ui.theme.screens.FavoriteScreen
import com.example.flightapp.ui.theme.screens.HomeDestination
import com.example.flightapp.ui.theme.screens.HomeScreen
import com.example.flightapp.ui.theme.screens.UserSelectedFlightScreen

/**
 * Top level composable that represents screens for the application.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FlightApp(navController: NavHostController = rememberNavController()) {
    InventoryNavHost(navController = navController)
}
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(UserSelectedFlightScreen.route) },
            )
        }
        composable(route = UserSelectedFlightScreen.route) {
            FavoriteScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) },
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}