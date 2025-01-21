package com.example.myapplication.repository

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.AllPelangganRespose
import com.example.myapplication.model.AllVillaRespose
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.service.PelangganService
import com.example.myapplication.service.VillaService
import java.io.IOException


interface PelangganRepository {
    suspend fun getPelanggan(): AllPelangganRespose

    suspend fun insertPelanggan(pelanggan: Pelanggan)

    suspend fun updatePelanggan(id_pelanggan: String, pelanggan: Pelanggan)

    suspend fun deletePelanggan(id_pelanggan: String)

    suspend fun getPelangganById(id_pelanggan: String): Pelanggan
}

class NetworkPelangganRepository(
    private val pelangganApiService: PelangganService
) : PelangganRepository{
    override suspend fun getPelanggan(): AllPelangganRespose =
        pelangganApiService.getAllPelanggan()


    override suspend fun getPelangganById(id_pelanggan: String): Pelanggan {
        return pelangganApiService.getPelangganById(id_pelanggan).data
    }

    override suspend fun insertPelanggan(pelanggan: Pelanggan) {
        pelangganApiService.insertPelanggan(pelanggan)
    }

    override suspend fun updatePelanggan(id_pelanggan: String, pelanggan: Pelanggan) {
        pelangganApiService.updatePelanggan(id_pelanggan, pelanggan)
    }

    override suspend fun deletePelanggan(id_pelanggan: String) {
        try{
            val response = pelangganApiService.deletePelanggan(id_pelanggan)
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
