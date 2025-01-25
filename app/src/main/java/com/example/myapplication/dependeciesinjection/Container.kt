package com.example.myapplication.dependeciesinjection

import com.example.myapplication.repository.*
import com.example.myapplication.service.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val villaRepository: VillaRepository
    val pelangganRepository: PelangganRepository
    val reservasiRepository: ReservasiRepository
    val reviewRepository: ReviewRepository  // Tambahkan repository untuk review
}

class Container : AppContainer {

    private val baseUrlVilla = "http://10.0.2.2:3000/api/villa/"
    private val baseUrlPelanggan = "http://10.0.2.2:3000/api/pelanggan/"
    private val baseUrlReservasi = "http://10.0.2.2:3000/api/reservasi/"
    private val baseUrlReview = "http://10.0.2.2:3000/api/review/"  // Base URL untuk review
    private val json = Json { ignoreUnknownKeys = true }

    // Create the Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrlVilla)  // Base URL bisa disesuaikan sesuai kebutuhan service
            .build()
    }

    private val villaService: VillaService by lazy {
        retrofit.create(VillaService::class.java)
    }

    private val pelangganService: PelangganService by lazy {
        retrofit.newBuilder().baseUrl(baseUrlPelanggan).build().create(PelangganService::class.java)
    }

    private val reservasiService: ReservasiService by lazy {
        retrofit.newBuilder().baseUrl(baseUrlReservasi).build().create(ReservasiService::class.java)
    }

    private val reviewService: ReviewService by lazy {
        retrofit.newBuilder().baseUrl(baseUrlReview).build().create(ReviewService::class.java)  // Service review
    }

    override val villaRepository: VillaRepository by lazy {
        NetworkVillaRepository(villaService)
    }

    override val pelangganRepository: PelangganRepository by lazy {
        NetworkPelangganRepository(pelangganService)
    }

    override val reservasiRepository: ReservasiRepository by lazy {
        NetworkReservasiRepository(reservasiService)
    }

    override val reviewRepository: ReviewRepository by lazy {
        NetworkReviewRepository(reviewService)  // Repository untuk review
    }
}
