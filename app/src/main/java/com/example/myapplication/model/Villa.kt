package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Villa (
    val id_villa: Int,
    val nama_villa: String,
    val alamat: String,
    val kamar_tersedia: Int
)

@Serializable
data class AllVillaRespose(
    val status: Boolean,
    val message: String,
    val data: List<Villa>
)

@Serializable
data class VillaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Villa
)

