package com.example.myapplication.service

import com.example.myapplication.model.AllPelangganRespose
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.PelangganDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PelangganService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getAllPelanggan(): AllPelangganRespose

    @GET("{id_pelanggan}")
    suspend fun getPelangganById(@Path("id_pelanggan") id_pelanggan: String): PelangganDetailResponse

    @POST("store")
    suspend fun insertPelanggan(@Body pelanggan: Pelanggan)

    @PUT("{id_pelanggan}")
    suspend fun updatePelanggan(@Path("id_pelanggan") id_pelanggan: String, @Body pelanggan: Pelanggan)

    @DELETE("{id_pelanggan}")
    suspend fun deletePelanggan(@Path("id_pelanggan") id_pelanggan: String): Response<Void>
}