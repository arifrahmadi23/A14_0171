package com.example.myapplication.repository

import com.example.myapplication.model.AllVillaRespose
import com.example.myapplication.model.Villa
import com.example.myapplication.service.VillaService
import java.io.IOException

interface VillaRepository {
    suspend fun getVilla(): AllVillaRespose

    suspend fun insertVilla(villa: Villa)

    suspend fun updateVilla(id_villa: String, villa: Villa)

    suspend fun deleteVilla(id_villa: String)

    suspend fun getVillaById(id_villa: String): Villa
}

class NetworkVillaRepository(
    private val villaApiService: VillaService
) : VillaRepository{
    override suspend fun getVilla(): AllVillaRespose =
        villaApiService.getAllVilla()

    override suspend fun getVillaById(id_villa: String): Villa {
        return villaApiService.getVillabyId(id_villa).data
    }

    override suspend fun insertVilla(villa: Villa) {
        villaApiService.insertVilla(villa)
    }

    override suspend fun updateVilla(id_villa: String, villa: Villa) {
        villaApiService.updateVilla(id_villa, villa)
    }

    override suspend fun deleteVilla(id_villa: String) {
        try{
            val response = villaApiService.deleteVilla(id_villa)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Villa. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

}