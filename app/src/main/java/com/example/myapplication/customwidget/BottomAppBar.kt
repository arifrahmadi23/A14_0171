package com.example.myapplication.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun BottomCustomWidget(
    modifier: Modifier = Modifier,
    onHomePelangganClick: () -> Unit,
    onHomeReservasiClick: () -> Unit, // Tambahkan parameter untuk tombol Reservasi
    onHomeReviewClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Ikon untuk navigasi ke HomePelanggan
            Icon(
                painter = painterResource(id = R.drawable.ic_pelanggan), // Ganti dengan ikon Anda
                contentDescription = "Home Pelanggan",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onHomePelangganClick() }
            )

            // Ikon untuk navigasi ke HomeReservasi
            Icon(
                painter = painterResource(id = R.drawable.ic_reservasi), // Ganti dengan ikon Anda
                contentDescription = "Home Reservasi",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onHomeReservasiClick() }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_review), // Ganti dengan ikon Anda
                contentDescription = "Home Review",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { onHomeReviewClick() }
            )
        }
    }
}
