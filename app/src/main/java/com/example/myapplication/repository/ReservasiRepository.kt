package com.example.myapplication.repository

import com.example.myapplication.model.AllPelangganRespose
import com.example.myapplication.model.AllReservasiResponse
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.service.PelangganService
import com.example.myapplication.service.ReservasiService
import java.io.IOException

interface ReservasiRepository {
    suspend fun getReservasi(): AllReservasiResponse

    suspend fun insertReservasi(reservasi: Reservasi)

    suspend fun updateReservasi(id_reservasi: String, reservasi: Reservasi)

    suspend fun deleteReservasi(id_reservasi: String)

    suspend fun getReservasiById(id_reservasi: String): Reservasi

}

class NetworkReservasiRepository(
    private val reservasiApiService: ReservasiService
) : ReservasiRepository{

    override suspend fun getReservasi(): AllReservasiResponse =
        reservasiApiService.getAllReservasi()


    override suspend fun getReservasiById(id_reservasi: String): Reservasi {
        return reservasiApiService.getReservasiById(id_reservasi).data
    }

    override suspend fun insertReservasi(reservasi: Reservasi) {
        reservasiApiService.insertReservasi(reservasi)
    }

    override suspend fun updateReservasi(id_reservasi: String, reservasi: Reservasi) {
        reservasiApiService.updateReservasi(id_reservasi, reservasi)
    }

    override suspend fun deleteReservasi(id_reservasi: String) {
        try{
            val response = reservasiApiService.deleteReservasi(id_reservasi)
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