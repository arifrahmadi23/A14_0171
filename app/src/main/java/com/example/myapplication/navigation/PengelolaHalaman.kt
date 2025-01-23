package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.pelanggan.DestinasiDetailPelanggan
import com.example.myapplication.ui.view.pelanggan.DestinasiEntryPelanggan
import com.example.myapplication.ui.view.pelanggan.DestinasiHomePelanggan
import com.example.myapplication.ui.view.pelanggan.DetailScreenPelanggan
import com.example.myapplication.ui.view.pelanggan.EntryPelangganScreen
import com.example.myapplication.ui.view.pelanggan.HomeScreenPelanggan
import com.example.myapplication.ui.view.villa.DestinasiDetailVilla
import com.example.myapplication.ui.view.villa.DestinasiEntryVilla
import com.example.myapplication.ui.view.villa.DestinasiHomeVilla
import com.example.myapplication.ui.view.villa.DestinasiUpdateVilla
import com.example.myapplication.ui.view.villa.DetailScreenVilla
import com.example.myapplication.ui.view.villa.EntryVillaScreen
import com.example.myapplication.ui.view.villa.HomeScreen
import com.example.myapplication.ui.view.villa.UpdateScreenVilla

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeVilla.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomeVilla.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate((DestinasiEntryVilla.route))},
                navigateToHomePelanggan = { navController.navigate((DestinasiHomePelanggan.route)) },
                onDetailClick = { idVilla ->
                    navController.navigate("${DestinasiDetailVilla.route}/$idVilla")
                }
            )
        }
        composable(DestinasiHomePelanggan.route){
            HomeScreenPelanggan(
                navigateBack = { navController.popBackStack() },
                navigateToItemEntry = {navController.navigate((DestinasiEntryPelanggan.route))},
                onDetailClick = {idPelanggan ->
                    navController.navigate("${DestinasiDetailPelanggan.route}/$idPelanggan")
                }

            )
        }

        composable(DestinasiDetailPelanggan.routeWithArg){ backStackEntry ->
            val IdPelanggan = backStackEntry.arguments?.getString(DestinasiDetailPelanggan.IdPelanggan) ?: ""
            DetailScreenPelanggan(
                navigateBack = { navController.popBackStack() },
//                onEditClick = {
//                    // Navigasi menuju halaman update
//                    navController.navigate("${DestinasiUpdatePelanggan.route}/$IdPelanggan")
//                }
            )
        }

        composable(DestinasiEntryPelanggan.route) {
            EntryPelangganScreen(navigateBack = {
                navController.navigate(DestinasiHomePelanggan.route){
                    popUpTo(DestinasiHomePelanggan.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(DestinasiEntryVilla.route) {
            EntryVillaScreen(navigateBack = {
                navController.navigate(DestinasiHomeVilla.route) {
                    popUpTo(DestinasiHomeVilla.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(DestinasiDetailVilla.routeWithArg) { backStackEntry ->
            val IdVilla = backStackEntry.arguments?.getString(DestinasiDetailVilla.IdVilla) ?: ""
            DetailScreenVilla(
                navigateBack = { navController.popBackStack() },
                onEditClick = {
                    // Navigasi menuju halaman update
                    navController.navigate("${DestinasiUpdateVilla.route}/$IdVilla")
                }
            )
        }

        composable(DestinasiUpdateVilla.routeWithArg) { backStackEntry ->
            val IdVilla = backStackEntry.arguments?.getString(DestinasiUpdateVilla.IdVilla) ?: ""
            UpdateScreenVilla(
                navigateBack = { navController.popBackStack() },
                onNavigate = {
                    // Jika diperlukan, jalankan fungsi lain saat navigasi selesai
                    navController.navigate(DestinasiHomeVilla.route)
                }
            )
        }

    }
}