package com.example.myapplication.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.VillaRepository


class InsertViewModelVilla (private val villa: VillaRepository): ViewModel(){
    var uiStateVilla by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent){
        uiStateVilla = InsertUiState(insertUiEvent = insertUiEvent)
    }
    suspend fun insertMhs() {
        try {
            villa.insertVilla(uiStateVilla.insertUiEvent.toVilla())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val id_villa: Int = 0,
    val nama_villa: String = "",
    val alamat: String = "",
    val kamar_tersedia: Int = 0
)

fun InsertUiEvent.toVilla():Villa = Villa (
    id_villa = id_villa,
    nama_villa = nama_villa,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia
)


fun Villa.toInsertUiEvent(): InsertUiEvent = InsertUiEvent (
    id_villa = id_villa,
    nama_villa = nama_villa,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia
)