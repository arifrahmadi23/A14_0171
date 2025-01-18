package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Villa (
    val id_villa: String,
    val nama_villa: String,
    val alamat: String,
    val kamar_tersedia: String
)

