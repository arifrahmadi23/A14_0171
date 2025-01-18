package com.example.myapplication.repository

import com.example.myapplication.model.AllVillaRespose
import com.example.myapplication.model.Villa

interface VillaRepository {
    suspend fun getVilla(): AllVillaRespose

    suspend fun insertVilla(villa: Villa)

    suspend fun updateVilla(id_villa: String, villa: Villa)

    suspend fun deleteVilla(id_villa: String)

    suspend fun getVillaById(id_villa: String): Villa

}