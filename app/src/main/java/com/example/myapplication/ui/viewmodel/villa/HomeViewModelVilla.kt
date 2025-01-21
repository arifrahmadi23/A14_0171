package com.example.myapplication.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.VillaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeVillaUiState{
    data class Success(val villa: List<Villa>): HomeVillaUiState()
    object Error : HomeVillaUiState()
    object Loading : HomeVillaUiState()
}

class HomeViewModelVilla(private val villa:VillaRepository): ViewModel(){
    var villaUiState: HomeVillaUiState by mutableStateOf(HomeVillaUiState.Loading)
        private set

    init {
        getVilla()
    }

    fun getVilla(){
        viewModelScope.launch {
            villaUiState = HomeVillaUiState.Loading
            villaUiState = try {
                HomeVillaUiState.Success(villa.getVilla().data)
            }catch (e : IOException){
                HomeVillaUiState.Error
            }catch (e : HttpException){
                HomeVillaUiState.Error
            }
        }
    }
}