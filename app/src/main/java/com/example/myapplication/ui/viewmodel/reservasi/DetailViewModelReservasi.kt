package com.example.myapplication.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.ui.view.pelanggan.DestinasiDetailPelanggan
import com.example.myapplication.ui.view.reservasi.DestinasiDetailReservasi
import com.example.myapplication.ui.viewmodel.pelanggan.DetailUiStatePelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.HomePelangganUiState
import com.example.myapplication.ui.viewmodel.pelanggan.InsertUiEventPelanggan
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiStateReservasi{
    data class Success(val reservasi: Reservasi) : DetailUiStateReservasi()
    object Error : DetailUiStateReservasi()
    object Loading : DetailUiStateReservasi()
}

class DetailViewModelReservasi(
    savedStateHandle: SavedStateHandle,
    private val reservasi: ReservasiRepository
): ViewModel(){
    var reservasiUiState : DetailUiStateReservasi by mutableStateOf(DetailUiStateReservasi.Loading)
        private set
    private val _id_reservasi: String = checkNotNull(savedStateHandle[DestinasiDetailReservasi.IdReservasi])

    init {
        getReservasibyId()
    }

    fun getReservasibyId(){
        viewModelScope.launch {
            reservasiUiState = DetailUiStateReservasi.Loading
            reservasiUiState = try {
                DetailUiStateReservasi.Success(reservasi.getReservasiById(_id_reservasi))
            } catch (e: IOException) {
                DetailUiStateReservasi.Error
            } catch (e: HttpException) {
                DetailUiStateReservasi.Error
            }
        }
    }
    fun deleteReservasi(id_Reservasi: Int){
        viewModelScope.launch {
            try {
                reservasi.deleteReservasi(id_Reservasi.toString())

            }catch (e: IOException){
                HomePelangganUiState.Error
            }catch (e: HttpException){
                HomePelangganUiState.Error
            }
        }
    }
}






fun Reservasi.toResevasilUiEvent(): InsertUiEventReservasi {
    return InsertUiEventReservasi(
        id_reservasi = id_reservasi,
        id_villa = id_villa,
        id_pelanggan = id_pelanggan,
        check_in = check_in,
        check_out = check_out,
        jumlah_kamar = jumlah_kamar,
        nama_villa = nama_villa,
        nama_pelanggan = nama_pelanggan
    )
}