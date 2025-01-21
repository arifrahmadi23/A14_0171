package com.example.myapplication

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.ui.viewmodel.pelanggan.HomeViewModelPelanggan
import com.example.myapplication.ui.viewmodel.villa.DetailViewModelVilla
import com.example.myapplication.ui.viewmodel.villa.HomeViewModelVilla
import com.example.myapplication.ui.viewmodel.villa.InsertViewModelVilla
import com.example.myapplication.ui.viewmodel.villa.UpdateViewModelVilla

object PenyediaViewModel{
    val Factory = viewModelFactory {
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

        initializer {
            HomeViewModelPelanggan(aplikasiVilla().container.pelangganRepository)
        }
    }
}


fun CreationExtras.aplikasiVilla(): VillaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VillaApplication)

