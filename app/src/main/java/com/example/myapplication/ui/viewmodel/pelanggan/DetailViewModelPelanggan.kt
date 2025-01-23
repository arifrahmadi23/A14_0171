package com.example.myapplication.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.view.pelanggan.DestinasiDetailPelanggan
import com.example.myapplication.ui.view.villa.DestinasiDetailVilla
import com.example.myapplication.ui.viewmodel.villa.DetailUiStateVilla
import com.example.myapplication.ui.viewmodel.villa.HomeVillaUiState
import com.example.myapplication.ui.viewmodel.villa.InsertUiEvent
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiStatePelanggan{
    data class Success(val pelanggan: Pelanggan) : DetailUiStatePelanggan()
    object Error : DetailUiStatePelanggan()
    object Loading : DetailUiStatePelanggan()
}

class DetailViewModelPelanggan(
    savedStateHandle: SavedStateHandle,
    private val pelanggan: PelangganRepository
): ViewModel(){
    var pelangganUiState : DetailUiStatePelanggan by mutableStateOf(DetailUiStatePelanggan.Loading)
        private set
    private val _id_pelanggan: String = checkNotNull(savedStateHandle[DestinasiDetailPelanggan.IdPelanggan])

    init {
        getPelangganbyId()
    }

    fun getPelangganbyId(){
        viewModelScope.launch {
            pelangganUiState = DetailUiStatePelanggan.Loading
            pelangganUiState = try {
                DetailUiStatePelanggan.Success(pelanggan.getPelangganById(_id_pelanggan))
            } catch (e: IOException) {
                DetailUiStatePelanggan.Error
            } catch (e: HttpException) {
                DetailUiStatePelanggan.Error
            }
        }
    }
    fun deletePelanggan(id_Pelanggan: Int){
        viewModelScope.launch {
            try {
                pelanggan.deletePelanggan(id_Pelanggan.toString())

            }catch (e: IOException){
                HomePelangganUiState.Error
            }catch (e: HttpException){
                HomePelangganUiState.Error
            }
        }
    }
}


fun Pelanggan.toDetailUiEvent(): InsertUiEventPelanggan {
    return InsertUiEventPelanggan(
        id_pelanggan = id_pelanggan,
        nama_pelanggan = nama_pelanggan,
        no_hp = no_hp
    )
}