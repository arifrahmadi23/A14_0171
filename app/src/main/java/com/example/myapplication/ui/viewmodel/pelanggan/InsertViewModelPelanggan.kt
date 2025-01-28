package com.example.myapplication.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.viewmodel.villa.FormErrorStateVilla
import com.example.myapplication.ui.viewmodel.villa.InsertUiEvent
import com.example.myapplication.ui.viewmodel.villa.InsertUiState
import com.example.myapplication.ui.viewmodel.villa.toVilla


class InsertViewModelPelanggan (private val pelanggan: PelangganRepository): ViewModel(){
    var uiStatePelanggan by mutableStateOf(InsertUiStatePelanggan())
        private set

    fun updateInsertPelangganState(insertUiEventPelanggan: InsertUiEventPelanggan){
        uiStatePelanggan = InsertUiStatePelanggan(insertUiEventPelanggan = insertUiEventPelanggan)
    }

    fun validateFieldsPelanggan(): Boolean {
        val event = uiStatePelanggan.insertUiEventPelanggan
        val errorState = FormErrorStatePelanggan(
            nama_pelanggan = if  (event.nama_pelanggan.isNotEmpty()) null else "Nama tidak boleh kosong",
            no_hp = if (event.no_hp.isNotEmpty()) null else "Nomor Handphone tidak bolej kosong"
        )
        uiStatePelanggan = uiStatePelanggan.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertPelanggan() {
        val currentEvent = uiStatePelanggan.insertUiEventPelanggan
        if (validateFieldsPelanggan()) {
            try {
                pelanggan.insertPelanggan(uiStatePelanggan.insertUiEventPelanggan.toPelanggan())
                uiStatePelanggan = uiStatePelanggan.copy(
                    snackBarMessage = "Data berhasil disimpan",
                    insertUiEventPelanggan = InsertUiEventPelanggan(),
                    isEntryValid = FormErrorStatePelanggan()
                )
            } catch (e: Exception) {
                uiStatePelanggan = uiStatePelanggan.copy(
                    snackBarMessage = "Data gagal disimpan"
                )
            }
        } else {
            uiStatePelanggan = uiStatePelanggan.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }
}

data class InsertUiStatePelanggan(
    val insertUiEventPelanggan: InsertUiEventPelanggan = InsertUiEventPelanggan(),
    val isEntryValid: FormErrorStatePelanggan = FormErrorStatePelanggan(),
    val snackBarMessage: String? = null
)

data class InsertUiEventPelanggan(
    val id_pelanggan: Int = 0,
    val nama_pelanggan: String = "",
    val no_hp: String = ""
)
data class FormErrorStatePelanggan(
    val nama_pelanggan: String? = null,
    val no_hp: String? = null
){
    fun isValid(): Boolean{
        return nama_pelanggan == null && no_hp == null
    }
}



fun InsertUiEventPelanggan.toPelanggan():Pelanggan = Pelanggan (
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)




fun Pelanggan.toInsertUiEvent(): InsertUiEventPelanggan = InsertUiEventPelanggan (
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)