package com.example.myapplication

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.ui.viewmodel.pelanggan.DetailViewModelPelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.HomeViewModelPelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.InsertViewModelPelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.UpdateViewModelPelanggan
import com.example.myapplication.ui.viewmodel.reservasi.DetailViewModelReservasi
import com.example.myapplication.ui.viewmodel.reservasi.HomeViewModelReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertViewModelReservasi
import com.example.myapplication.ui.viewmodel.reservasi.UpdateViewModelReservasi
import com.example.myapplication.ui.viewmodel.review.HomeViewModelReview
//import com.example.myapplication.ui.viewmodel.reservasi.UpdateViewModelReservasi
import com.example.myapplication.ui.viewmodel.villa.DetailViewModelVilla
import com.example.myapplication.ui.viewmodel.villa.HomeViewModelVilla
import com.example.myapplication.ui.viewmodel.villa.InsertViewModelVilla
import com.example.myapplication.ui.viewmodel.villa.UpdateViewModelVilla

object PenyediaViewModel{
    val Factory = viewModelFactory {

        //VILLA
        initializer {
            HomeViewModelVilla(aplikasiVilla().container.villaRepository)
        }

        initializer {
            InsertViewModelVilla(aplikasiVilla().container.villaRepository)
        }
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            DetailViewModelVilla(
                savedStateHandle = savedStateHandle,
                villa = aplikasiVilla().container.villaRepository
            )
        }
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            UpdateViewModelVilla(
                savedStateHandle = savedStateHandle,
                villaRepository = aplikasiVilla().container.villaRepository
            )
        }


        //PELANGGAN
        initializer {
            HomeViewModelPelanggan(aplikasiVilla().container.pelangganRepository)
        }
        initializer {
            InsertViewModelPelanggan(aplikasiVilla().container.pelangganRepository)
        }
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            DetailViewModelPelanggan(
                savedStateHandle = savedStateHandle,
                pelanggan = aplikasiVilla().container.pelangganRepository
            )
        }
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            UpdateViewModelPelanggan(
                savedStateHandle = savedStateHandle,
                pelangganRepository = aplikasiVilla().container.pelangganRepository)
        }


        //RESERVASI
        initializer {
            HomeViewModelReservasi(aplikasiVilla().container.reservasiRepository)
        }

        initializer {
            InsertViewModelReservasi(
                reservasiRepository = aplikasiVilla().container.reservasiRepository,
                villaRepository = aplikasiVilla().container.villaRepository,
                pelangganRepository = aplikasiVilla().container.pelangganRepository
            )
        }

        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            UpdateViewModelReservasi(
                savedStateHandle = savedStateHandle,
                reservasiRepository = aplikasiVilla().container.reservasiRepository,
                villaRepository = aplikasiVilla().container.villaRepository,
                pelangganRepository = aplikasiVilla().container.pelangganRepository
            )
        }

        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            DetailViewModelReservasi(
                savedStateHandle = savedStateHandle,
                reservasi = aplikasiVilla().container.reservasiRepository
            )
        }

        //REVIEW
        initializer {
            HomeViewModelReview(aplikasiVilla().container.reviewRepository)
        }
    }
}


fun CreationExtras.aplikasiVilla(): VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)

