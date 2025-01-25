package com.example.myapplication.ui.view.pelanggan

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
import com.example.myapplication.model.Villa
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.villa.ComponentDetailVilla
import com.example.myapplication.ui.view.villa.DestinasiDetailVilla
import com.example.myapplication.ui.view.villa.ItemDetailVilla
import com.example.myapplication.ui.viewmodel.pelanggan.DetailUiStatePelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.DetailViewModelPelanggan
import com.example.myapplication.ui.viewmodel.villa.DetailUiStateVilla
import com.example.myapplication.ui.viewmodel.villa.DetailViewModelVilla


object DestinasiDetailPelanggan : DestinasiNavigasi {
    override val route = "detailPelanggan"
    override val titleRes = "Detail Pelanggan"
    const val IdPelanggan = "idpelanggan"
    val routeWithArg = "$route/{$IdPelanggan}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPelanggan(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelPelanggan = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { }
) {
    // Scaffold untuk mengatur tampilan atas dan FAB
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPelanggan.titleRes,
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
            when (val state = viewModel.pelangganUiState) {
                is DetailUiStatePelanggan.Loading -> {
                    // Tampilkan indikator loading jika data sedang diambil
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStatePelanggan.Error -> {
                    // Tampilkan pesan error jika terjadi kesalahan
                    Text("Error loading data", modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStatePelanggan.Success -> {
                    ItemDetailPelanggan (
                        pelanggan = state.pelanggan,
                        onDeleteClick = { pelanggan -> viewModel.deletePelanggan(pelanggan.id_pelanggan) },
                        onEditClick = { pelanggan -> onEditClick(pelanggan.id_pelanggan.toString()) },
                        navigateBack = navigateBack// Handle klik delete
                    )

                }

            }
        }
    }
}

@Composable
fun ItemDetailPelanggan(
    modifier: Modifier = Modifier,
    pelanggan: Pelanggan,
    onDeleteClick: (Pelanggan) -> Unit, // Tambahkan properti onDeleteClick
    onEditClick: (Pelanggan) -> Unit,
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
                    text = pelanggan.nama_pelanggan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(pelanggan)
                    navigateBack()}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Pelanggan",
                        tint = MaterialTheme.colorScheme.error // Warna merah untuk delete
                    )
                }
                IconButton(onClick = {
                    onEditClick(pelanggan)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Villa",
                        tint = MaterialTheme.colorScheme.primary // Warna primary untuk edit
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailPelanggan(judul = "ID Pelanggan", isinya = pelanggan.id_pelanggan.toString())
            ComponentDetailPelanggan(judul = "Nama Pelanggan", isinya = pelanggan.nama_pelanggan)
            ComponentDetailPelanggan(judul = "Nomor Handphone", isinya = pelanggan.no_hp)
        }
    }
}


@Composable
fun ComponentDetailPelanggan(
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