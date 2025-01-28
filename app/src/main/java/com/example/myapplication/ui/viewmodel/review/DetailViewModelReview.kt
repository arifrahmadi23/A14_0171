package com.example.myapplication.ui.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Review
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.repository.ReviewRepository
import com.example.myapplication.ui.view.reservasi.DestinasiDetailReservasi
import com.example.myapplication.ui.view.review.DestinasiDetailReview
import com.example.myapplication.ui.viewmodel.pelanggan.HomePelangganUiState
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailUiStateReview{
    data class Success(val review: Review) : DetailUiStateReview()
    object Error : DetailUiStateReview()
    object Loading : DetailUiStateReview()
}

class DetailViewModelReview(
    savedStateHandle: SavedStateHandle,
    private val review: ReviewRepository
): ViewModel(){
    var reviewUiState : DetailUiStateReview by mutableStateOf(DetailUiStateReview.Loading)
        private set
    private val _id_review: String = checkNotNull(savedStateHandle[DestinasiDetailReview.IdReview])

    init {
        getReviewbyId()
    }

    fun getReviewbyId(){
        viewModelScope.launch {
            reviewUiState = DetailUiStateReview.Loading
            reviewUiState = try {
                DetailUiStateReview.Success(review.getReviewById(_id_review))
            } catch (e: IOException) {
                DetailUiStateReview.Error
            } catch (e: HttpException) {
                DetailUiStateReview.Error
            }
        }
    }
    fun deleteReview(id_Review: Int){
        viewModelScope.launch {
            try {
                review.deleteReview(id_Review.toString())

            }catch (e: IOException){
                HomeReviewUiState.Error
            }catch (e: HttpException){
                HomeReviewUiState.Error
            }
        }
    }
}