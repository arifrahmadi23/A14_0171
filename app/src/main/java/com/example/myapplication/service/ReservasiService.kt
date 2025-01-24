package com.example.myapplication.service

import com.example.myapplication.model.AllPelangganRespose
import com.example.myapplication.model.AllReservasiResponse
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.PelangganDetailResponse
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.ReservasiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllReservasi(): AllReservasiResponse

    @GET("{id_pelanggan}")
    suspend fun getReservasiById(@Path("id_reservasi") id_reservasi: String): ReservasiDetailResponse

    @POST("store")
    suspend fun insertReservasi(@Body reservasi: Reservasi)

    @PUT("{id_reservasi}")
    suspend fun updateReservasi(@Path("id_reservasi") id_reservasi: String, @Body reservasi: Reservasi)

    @DELETE("{id_reservasi}")
    suspend fun deleteReservasi(@Path("id_reservasi") id_reservasi: String): Response<Void>
}