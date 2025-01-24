package com.example.myapplication.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.ui.viewmodel.pelanggan.HomePelangganUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeReservasiUiState{
    data class Success(val reservasi: List<Reservasi>): HomeReservasiUiState()
    object Error : HomeReservasiUiState()
    object Loading : HomeReservasiUiState()
}

class HomeViewModelReservasi(private val reservasi: ReservasiRepository): ViewModel(){
    var reservasiUiState: HomeReservasiUiState by mutableStateOf(HomeReservasiUiState.Loading)
        private set

    init {
        getReservasi()
    }

    fun getReservasi(){
        viewModelScope.launch {
            reservasiUiState = HomeReservasiUiState.Loading
            reservasiUiState = try {
                HomeReservasiUiState.Success(reservasi.getReservasi().data)
            }catch (e : IOException){
                HomeReservasiUiState.Error
            }catch (e : HttpException){
                HomeReservasiUiState.Error
            }
        }
    }
}