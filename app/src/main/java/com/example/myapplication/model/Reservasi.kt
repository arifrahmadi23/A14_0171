package com.example.myapplication.model

import kotlinx.serialization.Serializable


@Serializable
data class Reservasi(
    val id_reservasi: Int,
    val id_villa: Int,
    val id_pelanggan: Int,
    val check_in: String,
    val check_out: String,
    val jumlah_kamar: Int,
    val nama_villa: String, // Dapat digunakan untuk menampilkan nama villa secara langsung
    val nama_pelanggan: String // Dapat digunakan untuk menampilkan nama pelanggan secara langsung
)

@Serializable
data class AllReservasiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Reservasi>
)

@Serializable
data class ReservasiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Reservasi
)
