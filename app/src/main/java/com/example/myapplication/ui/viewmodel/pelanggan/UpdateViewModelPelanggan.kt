package com.example.myapplication.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.view.pelanggan.DestinasiUpdatePelanggan
import com.example.myapplication.ui.view.villa.DestinasiUpdateVilla
import com.example.myapplication.ui.viewmodel.villa.InsertUiEvent
import com.example.myapplication.ui.viewmodel.villa.InsertUiState
import com.example.myapplication.ui.viewmodel.villa.toUiStateVillaUpdate
import com.example.myapplication.ui.viewmodel.villa.toVilla
import kotlinx.coroutines.launch

class UpdateViewModelPelanggan(
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
): ViewModel() {

    var uiState by mutableStateOf(InsertUiStatePelanggan())
        private set

    private val _id_pelanggan: String = checkNotNull(savedStateHandle[DestinasiUpdatePelanggan.IdPelanggan])

    init {
        viewModelScope.launch {
            val pelanggan = pelangganRepository.getPelangganById(_id_pelanggan)
            uiState = pelanggan.toUiStatePelangganUpdate() // Mengonversi Mahasiswa menjadi InsertUiState
        }
    }

    // Fungsi update untuk mengirimkan data mahasiswa ke repository
    fun updatePelanggan() {
        viewModelScope.launch {
            try {
                // Mengonversi InsertUiEvent menjadi Mahasiswa
                val pelanggan = uiState.insertUiEventPelanggan.toPelanggan()
                pelangganRepository.updatePelanggan(_id_pelanggan, pelanggan)  // Menyertakan nim dan mahasiswa
            } catch (e: Exception) {
                e.printStackTrace()  // Tangani jika ada error dalam update
            }
        }
    }

    // Fungsi untuk memperbarui state sesuai dengan event update
    fun updateState(insertUiEventPelanggan: InsertUiEventPelanggan) {
        uiState = uiState.copy(insertUiEventPelanggan = insertUiEventPelanggan)
    }
}


fun Pelanggan.toUiStatePelangganUpdate(): InsertUiStatePelanggan = InsertUiStatePelanggan(
    insertUiEventPelanggan = this.toDetailUiEvent()
)