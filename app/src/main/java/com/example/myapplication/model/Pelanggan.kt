package com.example.myapplication.model

import kotlinx.serialization.Serializable


@Serializable
data class Pelanggan (
    val id_pelanggan: Int,
    val nama_pelanggan: String,
    val no_hp: String,
)

@Serializable
data class AllPelangganRespose(
    val status: Boolean,
    val message: String,
    val data: List<Pelanggan>
)

@Serializable
data class PelangganDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pelanggan
)