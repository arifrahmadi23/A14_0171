package com.example.myapplication.ui.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Review
import com.example.myapplication.model.Villa
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.repository.ReviewRepository
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiEventReservasi
import kotlinx.coroutines.launch


class InsertViewModelReview(
    private val reviewRepository: ReviewRepository,
    private val reservasiRepository: ReservasiRepository
) : ViewModel(){

    var uiStateReview by mutableStateOf(InsertUiStateReview())
        private set

    var listReservasi by mutableStateOf<List<Reservasi>>(listOf())
        private set

    init {
        loadExistingData()
    }

    fun loadExistingData(){
        viewModelScope.launch {
            try {
                val reservasiResponse = reservasiRepository.getReservasi()
                listReservasi = reservasiResponse.data
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateInsertReviewState(insertUiEventReview: InsertUiEventReview){
        uiStateReview = uiStateReview.copy(insertUiEventReview = insertUiEventReview)
    }

    fun insertReview(){
        viewModelScope.launch {
            try {
                reviewRepository.insertReview(uiStateReview.insertUiEventReview.toReview())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}


data class InsertUiStateReview(
    val insertUiEventReview: InsertUiEventReview = InsertUiEventReview(),
    val idReservasiList: List<Reservasi> = emptyList(),

)

data class InsertUiEventReview(
    val id_review: Int = 0,
    val id_reservasi: Int = 0,
    val nilai: String = "",
    val komentar: String = "",
    val nama_villa: String = ""

)

fun InsertUiEventReview.toReview(): Review = Review (
    id_review = id_review,
    id_reservasi = id_reservasi,
    nilai = nilai,
    komentar = komentar,
    nama_villa = nama_villa

)

fun Review.toInsertUiEvent(): InsertUiEventReview = InsertUiEventReview (
    id_review = id_review,
    id_reservasi = id_reservasi,
    nilai = nilai,
    komentar = komentar
)