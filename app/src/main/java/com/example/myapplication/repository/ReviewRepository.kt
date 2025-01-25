package com.example.myapplication.repository

import com.example.myapplication.model.AllReviewResponse
import com.example.myapplication.model.Review
import com.example.myapplication.service.ReviewService
import java.io.IOException

interface ReviewRepository{
    suspend fun getReview(): AllReviewResponse

    suspend fun insertReview(review: Review)

    suspend fun updateReview(id_review: String, review: Review)

    suspend fun deleteReview(id_review: String)

    suspend fun getReviewById(id_review: String): Review
}

class NetworkReviewRepository(
    private val reviewApiService: ReviewService
) : ReviewRepository {
    override suspend fun getReview(): AllReviewResponse =
        reviewApiService.getAllReview()

    override suspend fun getReviewById(id_review: String): Review {
        return reviewApiService.getReviewById(id_review).data
    }

    override suspend fun insertReview(review: Review) {
        reviewApiService.insertReview(review)
    }

    override suspend fun updateReview(id_review: String, review: Review) {
        reviewApiService.updateReview(id_review, review)
    }

    override suspend fun deleteReview(id_review: String) {
        try{
            val response = reviewApiService.deleteReview(id_review)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Villa. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

}