package com.example.myapplication.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.VillaRepository
import kotlinx.coroutines.launch


class InsertViewModelVilla (private val villa: VillaRepository): ViewModel() {
    var uiStateVilla by mutableStateOf(InsertUiState())
        private set

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiStateVilla = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun valideteFieldsVilla(): Boolean {
        val event = uiStateVilla.insertUiEvent
        val errorState = FormErrorStateVilla(
            nama_villa = if (event.nama_villa.isNotEmpty()) null else "Nama Villa tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat Villa tidak boleh kosong",
            kamar_tersedia = if (event.kamar_tersedia.toString().isNotEmpty()) null else "Alamat Villa tidak boleh kosong",

        )
        uiStateVilla = uiStateVilla.copy(isEntryValid = errorState)
        return errorState.isValid()

    }

    fun insertVilla() {
        val currentEvent = uiStateVilla.insertUiEvent
        if (valideteFieldsVilla()) {
            viewModelScope.launch {
                try {
                    villa.insertVilla(uiStateVilla.insertUiEvent.toVilla())
                    uiStateVilla = uiStateVilla.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        insertUiEvent = InsertUiEvent(),
                        isEntryValid = FormErrorStateVilla()
                    )
                } catch (e: Exception) {
                    uiStateVilla = uiStateVilla.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateVilla = uiStateVilla.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }
}



data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: FormErrorStateVilla = FormErrorStateVilla(),
    val snackBarMessage: String? = null
)

data class InsertUiEvent(
    val id_villa: Int = 0,
    val nama_villa: String = "",
    val alamat: String = "",
    val kamar_tersedia: Int = 0
)

data class FormErrorStateVilla(
    val nama_villa: String? = null,
    val alamat: String? = null,
    val kamar_tersedia: String? = null
){
    fun isValid(): Boolean{
        return nama_villa == null && alamat == null  && kamar_tersedia == null
    }
}


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