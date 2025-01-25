package com.example.myapplication.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.view.reservasi.DestinasiUpdateReservasi
import kotlinx.coroutines.launch

class UpdateViewModelReservasi(
    savedStateHandle: SavedStateHandle,
    private val reservasiRepository: ReservasiRepository,
    private val villaRepository: VillaRepository,
    private val pelangganRepository: PelangganRepository,

) : ViewModel() {

    private val _id_Reservasi: String = checkNotNull(savedStateHandle[DestinasiUpdateReservasi.IdReservasi])

    var uiStateReservasi by mutableStateOf(InsertUiStateReservasi())
        private set

    var listVilla by mutableStateOf<List<Villa>>(listOf())
        private set

    var listPelanggan by mutableStateOf<List<Pelanggan>>(listOf())
        private set


    init {
        loadReservasi()
        loadExistingData()
    }

    // Fungsi untuk memuat data sesi terapi berdasarkan ID
    private fun loadReservasi() {
        viewModelScope.launch {
            try {
                val reservasi = reservasiRepository.getReservasiById(_id_Reservasi)
                reservasi?.let {
                    uiStateReservasi = it.toUiStateReservasi()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memuat data terkait (Pasien, Terapis, Jenis Terapi)
    private fun loadExistingData() {
        viewModelScope.launch {
            try {
                listVilla = villaRepository.getVilla().data
                listPelanggan = pelangganRepository.getPelanggan().data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui state UI
    fun updateReservasiState(updateUiEventReservasi: InsertUiEventReservasi) {
        uiStateReservasi = uiStateReservasi.copy(insertUiEventReservasi = updateUiEventReservasi)
    }

    // Fungsi untuk menyimpan perubahan ke database
    fun updateReservasi() {
        viewModelScope.launch {
            try {
                reservasiRepository.updateReservasi(
                    _id_Reservasi,
                    uiStateReservasi.insertUiEventReservasi.toReservasi()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


fun Reservasi.toUiStateReservasi(): InsertUiStateReservasi {
    return InsertUiStateReservasi(
        insertUiEventReservasi = InsertUiEventReservasi(
            id_reservasi = id_reservasi,
            id_villa = id_villa,
            id_pelanggan = id_pelanggan,
            check_in = check_in,
            check_out = check_out,
            nama_villa = nama_villa,
            nama_pelanggan = nama_pelanggan
        )
    )
}