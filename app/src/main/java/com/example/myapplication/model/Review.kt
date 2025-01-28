package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id_review: Int,
    val id_reservasi: Int,
    val nilai: String,
    val komentar: String,
    val nama_villa: String

)

@Serializable
data class AllReviewResponse(
    val status: Boolean,
    val message: String,
    val data: List<Review>
)

@Serializable
data class ReviewDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Review
)
