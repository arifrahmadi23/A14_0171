package com.example.myapplication.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.view.villa.DestinasiUpdateVilla
import kotlinx.coroutines.launch

class UpdateViewModelVilla(
    savedStateHandle: SavedStateHandle,
    private val villaRepository: VillaRepository
): ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    private val _id_villa: String = checkNotNull(savedStateHandle[DestinasiUpdateVilla.IdVilla])

    init {
        viewModelScope.launch {
            val villa = villaRepository.getVillaById(_id_villa)
            uiState = villa.toUiStateVillaUpdate() // Mengonversi Mahasiswa menjadi InsertUiState
        }
    }

    // Fungsi update untuk mengirimkan data mahasiswa ke repository
    fun updateVilla() {
        viewModelScope.launch {
            try {
                // Mengonversi InsertUiEvent menjadi Mahasiswa
                val villa = uiState.insertUiEvent.toVilla()
                villaRepository.updateVilla(_id_villa, villa)  // Menyertakan nim dan mahasiswa
            } catch (e: Exception) {
                e.printStackTrace()  // Tangani jika ada error dalam update
            }
        }
    }

    // Fungsi untuk memperbarui state sesuai dengan event update
    fun updateState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }
}


fun Villa.toUiStateVillaUpdate(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent()
)