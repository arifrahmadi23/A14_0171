package com.example.myapplication.ui.view.reservasi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.pelanggan.ComponentDetailPelanggan
import com.example.myapplication.ui.view.pelanggan.DestinasiDetailPelanggan
import com.example.myapplication.ui.view.pelanggan.ItemDetailPelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.DetailUiStatePelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.DetailViewModelPelanggan
import com.example.myapplication.ui.viewmodel.reservasi.DetailUiStateReservasi
import com.example.myapplication.ui.viewmodel.reservasi.DetailViewModelReservasi


object DestinasiDetailReservasi : DestinasiNavigasi {
    override val route = "detailReservasi"
    override val titleRes = "Detail Reservasi"
    const val IdReservasi = "idresevasi"
    val routeWithArg = "$route/{$IdReservasi}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenReservasi(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelReservasi = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { }
) {
    // Scaffold untuk mengatur tampilan atas dan FAB
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailReservasi.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Padding dari innerPadding
                .padding(16.dp) // Padding tambahan agar lebih rapi
        ) {
            when (val state = viewModel.reservasiUiState) {
                is DetailUiStateReservasi.Loading -> {
                    // Tampilkan indikator loading jika data sedang diambil
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStateReservasi.Error -> {
                    // Tampilkan pesan error jika terjadi kesalahan
                    Text("Error loading data", modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStateReservasi.Success -> {
                    ItemDetailReservasi(
                        reservasi = state.reservasi,
                        onDeleteClick = { reservasi -> viewModel.deleteReservasi(reservasi.id_reservasi) },
                        onEditClick = { reservasi -> onEditClick(reservasi.id_reservasi.toString()) },
                        navigateBack = navigateBack// Handle klik delete
                    )

                }

            }
        }
    }
}



@Composable
fun ItemDetailReservasi(
    modifier: Modifier = Modifier,
    reservasi: Reservasi,

    onDeleteClick: (Reservasi) -> Unit, // Tambahkan properti onDeleteClick
    onEditClick: (Reservasi) -> Unit,
    navigateBack: () -> Unit,


    ) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reservasi.nama_villa,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(reservasi)
                    navigateBack()}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Reservasi",
                        tint = MaterialTheme.colorScheme.error // Warna merah untuk delete
                    )
                }
                IconButton(onClick = {
                    onEditClick(reservasi)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Resevasi",
                        tint = MaterialTheme.colorScheme.primary // Warna primary untuk edit
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailReservasi(judul = "Nama Pelanggan", isinya = reservasi.nama_pelanggan)
            ComponentDetailReservasi(judul = "Tanggal Check in", isinya = reservasi.check_in)
            ComponentDetailReservasi(judul = "Tanggal Check out", isinya = reservasi.check_out)
            ComponentDetailReservasi(judul = "Jumlah Kamar dipesan", isinya = reservasi.jumlah_kamar.toString())

        }
    }
}


@Composable
fun ComponentDetailReservasi(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,  // Menghapus bold
                color = MaterialTheme.colorScheme.onSurface  // Menyesuaikan warna teks
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,  // Menghapus bold
                color = MaterialTheme.colorScheme.onSurface  // Menggunakan warna standar untuk teks
            )
        )
    }
}