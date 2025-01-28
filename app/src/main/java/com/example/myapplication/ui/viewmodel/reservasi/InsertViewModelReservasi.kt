package com.example.myapplication.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.repository.VillaRepository
import kotlinx.coroutines.launch
import java.text.Normalizer.Form


class InsertViewModelReservasi(
    private val reservasiRepository: ReservasiRepository,
    private val villaRepository: VillaRepository,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {

    var uiStateReservasi by mutableStateOf(InsertUiStateReservasi())
        private set

    var listVilla by mutableStateOf<List<Villa>>(listOf())
        private set

    var listPelanggan by mutableStateOf<List<Pelanggan>>(listOf())
        private set

    init {
        loadExistingData()
    }

    fun loadExistingData(){
        viewModelScope.launch {
            try {
                val villaRespose = villaRepository.getVilla()
                listVilla = villaRespose.data
                val pelangganRespose = pelangganRepository.getPelanggan()
                listPelanggan = pelangganRespose.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertReservasiState(insertUiEventReservasi: InsertUiEventReservasi){
        uiStateReservasi = uiStateReservasi.copy(insertUiEventReservasi = insertUiEventReservasi)
    }

    fun insertReservasi(){
        viewModelScope.launch {
            try {
                val villa = listVilla.find { it.id_villa == uiStateReservasi.insertUiEventReservasi.id_villa }
                val jumlahKamarDipesan = uiStateReservasi.insertUiEventReservasi.jumlah_kamar
                if (villa == null) {
                    // Villa tidak ditemukan
                    println("Villa dengan ID ${uiStateReservasi.insertUiEventReservasi.id_villa} tidak ditemukan.")
                    return@launch
                }

                if (villa.kamar_tersedia < jumlahKamarDipesan) {
                    // Jika kamar yang tersedia kurang dari yang dipesan
                    println("Tidak ada kamar tersedia. Kamar tersedia: ${villa.kamar_tersedia}, Jumlah dipesan: $jumlahKamarDipesan")
                    return@launch
                }
                reservasiRepository.insertReservasi(uiStateReservasi.insertUiEventReservasi.toReservasi())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiStateReservasi(
    val insertUiEventReservasi: InsertUiEventReservasi = InsertUiEventReservasi(),
    val namaVillaList: List<Villa> = emptyList(),
    val namaPelanggan: List<Pelanggan> = emptyList(),

)


data class InsertUiEventReservasi(
    val id_reservasi: Int = 0,
    val id_villa: Int = 0,
    val id_pelanggan: Int = 0,
    val check_in: String = "",
    val check_out: String = "",
    val jumlah_kamar: Int = 1,
    val nama_villa: String = "",
    val nama_pelanggan: String = "",
)



fun InsertUiEventReservasi.toReservasi():Reservasi = Reservasi (
    id_reservasi = id_reservasi,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar,
    nama_villa = nama_villa,
    nama_pelanggan = nama_pelanggan
)



fun Reservasi.toInsertUiEvent(): InsertUiEventReservasi = InsertUiEventReservasi (
    id_reservasi = id_reservasi,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar,
    nama_villa = nama_villa,
    nama_pelanggan = nama_pelanggan
)