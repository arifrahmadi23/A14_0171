package com.example.myapplication.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.PelangganRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.ui.viewmodel.villa.HomeVillaUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePelangganUiState{
    data class Success(val pelanggan: List<Pelanggan>): HomePelangganUiState()
    object Error : HomePelangganUiState()
    object Loading : HomePelangganUiState()
}

class HomeViewModelPelanggan(private val pelanggan: PelangganRepository): ViewModel(){
    var pelangganUiState: HomePelangganUiState by mutableStateOf(HomePelangganUiState.Loading)
        private set

    init {
        getPelanggan()
    }

    fun getPelanggan(){
        viewModelScope.launch {
            pelangganUiState = HomePelangganUiState.Loading
            pelangganUiState = try {
                HomePelangganUiState.Success(pelanggan.getPelanggan().data)
            }catch (e : IOException){
                HomePelangganUiState.Error
            }catch (e : HttpException){
                HomePelangganUiState.Error
            }
        }
    }
}