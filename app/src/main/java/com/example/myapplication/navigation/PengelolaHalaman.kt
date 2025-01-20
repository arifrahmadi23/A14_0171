package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.viewvilla.DestinasiHomeVilla
import com.example.myapplication.ui.viewvilla.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeVilla.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomeVilla.route){
            HomeScreen(
                navigateToItemEntry = {}
            )
        }

    }
}