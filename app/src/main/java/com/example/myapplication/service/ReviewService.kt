package com.example.myapplication.service

import com.example.myapplication.model.AllReservasiResponse
import com.example.myapplication.model.AllReviewResponse
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.ReservasiDetailResponse
import com.example.myapplication.model.Review
import com.example.myapplication.model.ReviewDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllReview(): AllReviewResponse

    @GET("{id_review}")
    suspend fun getReviewById(@Path("id_review") id_review: String): ReviewDetailResponse

    @POST("store")
    suspend fun insertReview(@Body review: Review)

    @PUT("{id_review}")
    suspend fun updateReview(@Path("id_review") id_review: String, @Body review: Review)

    @DELETE("{id_review}")
    suspend fun deleteReview(@Path("id_review") id_review: String): Response<Void>
}











