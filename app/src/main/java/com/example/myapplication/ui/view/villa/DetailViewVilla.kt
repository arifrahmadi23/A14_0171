package com.example.myapplication.ui.view.villa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.myapplication.model.Villa
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.viewmodel.villa.DetailUiStateVilla
import com.example.myapplication.ui.viewmodel.villa.DetailViewModelVilla

object DestinasiDetailVilla : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Villa"
    const val IdVilla = "idvilla"
    val routeWithArg = "$route/{$IdVilla}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenVilla(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelVilla = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { }
) {
    // Scaffold untuk mengatur tampilan atas dan FAB
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailVilla.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(viewModel.villaUiState.toString()) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Villa")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Padding dari innerPadding
                .padding(16.dp) // Padding tambahan agar lebih rapi
        ) {
            when (val state = viewModel.villaUiState) {
                is DetailUiStateVilla.Loading -> {
                    // Tampilkan indikator loading jika data sedang diambil
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStateVilla.Error -> {
                    // Tampilkan pesan error jika terjadi kesalahan
                    Text("Error loading data", modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStateVilla.Success -> {
                    // Tampilkan data mahasiswa jika berhasil
                    ItemDetailVilla(villa = state.villa)
                }
            }
        }
    }
}

@Composable
fun ItemDetailVilla(
    modifier: Modifier = Modifier,
    villa: Villa
) {
    // Menggunakan Card untuk menampilkan data mahasiswa
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "ID Villa", isinya = villa.id_villa.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nama Villa", isinya = villa.nama_villa)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Alamat", isinya = villa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Jumlah Kamar Tersedia", isinya = villa.kamar_tersedia)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailMhs(
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
