package com.example.myapplication.ui.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Review
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.repository.ReviewRepository
import com.example.myapplication.ui.viewmodel.reservasi.HomeReservasiUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeReviewUiState{
    data class Success(val review: List<Review>): HomeReviewUiState()
    object Error : HomeReviewUiState()
    object Loading : HomeReviewUiState()
}

class HomeViewModelReview(private val review: ReviewRepository): ViewModel(){
    var reviewUiState: HomeReviewUiState by mutableStateOf(HomeReviewUiState.Loading)
        private set

    init {
        getReview()
    }

    fun getReview(){
        viewModelScope.launch {
            reviewUiState = HomeReviewUiState.Loading
            reviewUiState = try {
                HomeReviewUiState.Success(review.getReview().data)
            }catch (e : IOException){
                HomeReviewUiState.Error
            }catch (e : HttpException){
                HomeReviewUiState.Error
            }
        }
    }
}