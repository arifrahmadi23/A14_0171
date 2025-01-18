package com.example.myapplication.service

import com.example.myapplication.model.AllVillaRespose
import com.example.myapplication.model.Villa
import com.example.myapplication.model.VillaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VillaService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )


    @GET(".")
    suspend fun getAllVilla(): AllVillaRespose

    @GET("{id_villa}")
    suspend fun getVillabyId(@Path("id_villa") id_villa:String): VillaDetailResponse

    @POST("store")
    suspend fun insertVilla(@Body villa: Villa)

    @PUT("{id_villa}")
    suspend fun updateVilla(@Path("id_villa")id_villa: String, @Body villa: Villa)

    @DELETE("{id_villa}")
    suspend fun deleteVilla(@Path("id_villa") id_villa: String): Response<Void>
}