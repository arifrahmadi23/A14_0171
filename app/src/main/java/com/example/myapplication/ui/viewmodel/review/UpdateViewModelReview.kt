package com.example.myapplication.ui.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Review
import com.example.myapplication.repository.ReservasiRepository
import com.example.myapplication.repository.ReviewRepository
import com.example.myapplication.ui.view.review.DestinasiUpdateReview
import kotlinx.coroutines.launch


class UpdateViewModelReview(
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository,
    private val reservasiRepository: ReservasiRepository

    ) : ViewModel() {

    private val _id_Review: String = checkNotNull(savedStateHandle[DestinasiUpdateReview.IdReview])

    var uiStateReview by mutableStateOf(InsertUiStateReview())
        private set

    var listReservasi by mutableStateOf<List<Reservasi>>(listOf())
        private set

    init {
        loadReview()
        loadExistingData()
    }

    // Fungsi untuk memuat data sesi terapi berdasarkan ID
    private fun loadReview() {
        viewModelScope.launch {
            try {
                val review = reviewRepository.getReviewById(_id_Review)
                review?.let {
                    uiStateReview = it.toUiStateReview()
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
                listReservasi = reservasiRepository.getReservasi().data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk memperbarui state UI
    fun updateReviewState(updateUiEventReview: InsertUiEventReview) {
        uiStateReview = uiStateReview.copy(insertUiEventReview = updateUiEventReview)
    }

    // Fungsi untuk menyimpan perubahan ke database
    fun updateReview() {
        viewModelScope.launch {
            try {
                reviewRepository.updateReview(
                    _id_Review,
                    uiStateReview.insertUiEventReview.toReview()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


fun Review.toUiStateReview(): InsertUiStateReview {
    return InsertUiStateReview(
        insertUiEventReview = InsertUiEventReview(
            id_review = id_review,
            id_reservasi = id_reservasi,
            nama_villa = nama_villa,
            nilai = nilai,
            komentar = komentar
        )
    )
}