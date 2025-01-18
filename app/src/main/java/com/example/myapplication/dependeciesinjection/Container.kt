package com.example.myapplication.dependeciesinjection

import com.example.myapplication.repository.NetworkVillaRepository
import com.example.myapplication.repository.VillaRepository
import com.example.myapplication.service.VillaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val villaRepository: VillaRepository
}

class Container : AppContainer{

    private val baseUrl = "http://10.0.2.2:3000/api/villa/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retroit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val villaService: VillaService by lazy {
        retroit.create(VillaService::class.java)
    }

    override val villaRepository: VillaRepository by lazy {
        NetworkVillaRepository(villaService)
    }
}