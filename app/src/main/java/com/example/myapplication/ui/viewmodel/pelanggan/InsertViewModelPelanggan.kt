package com.example.myapplication.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.viewmodel.villa.InsertUiEvent
import com.example.myapplication.ui.viewmodel.villa.InsertUiState
import com.example.myapplication.ui.viewmodel.villa.toVilla


class InsertViewModelPelanggan (private val pelanggan: PelangganRepository): ViewModel(){
    var uiStatePelanggan by mutableStateOf(InsertUiStatePelanggan())
        private set

    fun updateInsertPelangganState(insertUiEventPelanggan: InsertUiEventPelanggan){
        uiStatePelanggan = InsertUiStatePelanggan(insertUiEventPelanggan = insertUiEventPelanggan)
    }
    suspend fun insertPelanggan() {
        try {
            pelanggan.insertPelanggan(uiStatePelanggan.insertUiEventPelanggan.toPelanggan())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class InsertUiStatePelanggan(
    val insertUiEventPelanggan: InsertUiEventPelanggan = InsertUiEventPelanggan()
)

data class InsertUiEventPelanggan(
    val id_pelanggan: Int = 0,
    val nama_pelanggan: String = "",
    val no_hp: String = ""
)

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