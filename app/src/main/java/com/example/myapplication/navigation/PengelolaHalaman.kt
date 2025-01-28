package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.pelanggan.DestinasiDetailPelanggan
import com.example.myapplication.ui.view.pelanggan.DestinasiEntryPelanggan
import com.example.myapplication.ui.view.pelanggan.DestinasiHomePelanggan
import com.example.myapplication.ui.view.pelanggan.DestinasiUpdatePelanggan
import com.example.myapplication.ui.view.pelanggan.DetailScreenPelanggan
import com.example.myapplication.ui.view.pelanggan.EntryPelangganScreen
import com.example.myapplication.ui.view.pelanggan.HomeScreenPelanggan
import com.example.myapplication.ui.view.pelanggan.UpdateScreenPelanggan
import com.example.myapplication.ui.view.reservasi.DestinasiDetailReservasi
import com.example.myapplication.ui.view.reservasi.DestinasiEntryReservasi
import com.example.myapplication.ui.view.reservasi.DestinasiHomeReservasi
import com.example.myapplication.ui.view.reservasi.DestinasiUpdateReservasi

import com.example.myapplication.ui.view.reservasi.DetailScreenReservasi
import com.example.myapplication.ui.view.reservasi.EntryBodyReservasi
import com.example.myapplication.ui.view.reservasi.HomeScreenReservasi
import com.example.myapplication.ui.view.reservasi.InsertReservasiScreen
import com.example.myapplication.ui.view.reservasi.UpdateReservasiScreen
import com.example.myapplication.ui.view.reservasi.UpdateReservasiScreen
import com.example.myapplication.ui.view.review.DestinasiEntryReview
import com.example.myapplication.ui.view.review.DestinasiHomeReview
import com.example.myapplication.ui.view.review.HomeScreenReview
import com.example.myapplication.ui.view.review.InsertReviewScreen
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
        //VILLA
        composable(DestinasiHomeVilla.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate((DestinasiEntryVilla.route))},
                navigateToHomePelanggan = { navController.navigate((DestinasiHomePelanggan.route)) },
                navigateToHomeReservasi = {navController.navigate((DestinasiHomeReservasi.route))},
                navigateToHomeReview = {navController.navigate((DestinasiHomeReview.route))},
                onDetailClick = { idVilla ->
                    navController.navigate("${DestinasiDetailVilla.route}/$idVilla")
                }
            )
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
                },
                onReserveClick = {navController.navigate((DestinasiEntryReservasi.route))}
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


        //PELANGGAN
        composable(DestinasiHomePelanggan.route){
            HomeScreenPelanggan(
                navigateBack = { navController.navigate(DestinasiHomeVilla.route) },
                navigateToItemEntry = {navController.navigate((DestinasiEntryPelanggan.route))},
                onDetailClick = {idPelanggan ->
                    navController.navigate("${DestinasiDetailPelanggan.route}/$idPelanggan")
                }

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
        composable(DestinasiDetailPelanggan.routeWithArg){ backStackEntry ->
            val idPelanggan = backStackEntry.arguments?.getString(DestinasiDetailPelanggan.IdPelanggan) ?: ""
            DetailScreenPelanggan(
                navigateBack = { navController.navigate(DestinasiHomePelanggan.route) },
                onEditClick = {
                    // Navigasi menuju halaman update
                    navController.navigate("${DestinasiUpdatePelanggan.route}/$idPelanggan")
                }
            )
        }
        composable(DestinasiUpdatePelanggan.routeWithArg) { backStackEntry ->
            val idPelanggan = backStackEntry.arguments?.getString(DestinasiUpdatePelanggan.IdPelanggan) ?: ""
            UpdateScreenPelanggan(
                navigateBack = { navController.navigate(DestinasiHomePelanggan.route) },

            )
        }


        //RESERVASI
        composable(DestinasiHomeReservasi.route){
            HomeScreenReservasi(
                navigateBack = { navController.navigate(DestinasiHomeVilla.route) },
                onDetailClick = {idReservasi ->
                    navController.navigate("${DestinasiDetailReservasi.route}/$idReservasi")
                }
            )
        }
        composable(DestinasiDetailReservasi.routeWithArg){ backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getString(DestinasiDetailReservasi.IdReservasi) ?: ""
            DetailScreenReservasi(
                navigateBack = { navController.navigate(DestinasiHomeReservasi.route) },
                onEditClick = {
                    // Navigasi menuju halaman update
                    navController.navigate("${DestinasiUpdateReservasi.route}/$idReservasi")
                }
            )
        }
        composable(DestinasiUpdateReservasi.routeWithArg) { backStackEntry ->
            val IdReservasi = backStackEntry.arguments?.getString(DestinasiUpdateReservasi.IdReservasi) ?: ""
            UpdateReservasiScreen(
                navigateBack = { navController.navigate(DestinasiHomeReservasi.route) },

            )
        }
        composable(DestinasiEntryReservasi.route) {
            InsertReservasiScreen(navigateBack = {
                navController.navigate(DestinasiHomeVilla.route) {
                    popUpTo(DestinasiHomeVilla.route) {
                        inclusive = true
                    }
                }
            })
        }

        //RREVIEW
        composable(DestinasiHomeReview.route){
            HomeScreenReview(
                navigateBack = { navController.navigate(DestinasiHomeVilla.route) },
                navigateToItemEntry = {navController.navigate((DestinasiEntryReview.route))},

//                onDetailClick = {idReview ->
//                    navController.navigate("${DestinasiDetailReview.route}/$idReview")
//                }

            )
        }
        composable(DestinasiEntryReview.route){
            InsertReviewScreen(
                navigateBack = {navController.navigate(DestinasiHomeReview.route)},

            )
        }
    }
}