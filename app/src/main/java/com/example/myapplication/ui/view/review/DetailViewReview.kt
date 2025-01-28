package com.example.myapplication.ui.view.review

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
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Review
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.reservasi.ComponentDetailReservasi
import com.example.myapplication.ui.view.reservasi.DestinasiDetailReservasi
import com.example.myapplication.ui.view.reservasi.ItemDetailReservasi
import com.example.myapplication.ui.viewmodel.reservasi.DetailUiStateReservasi
import com.example.myapplication.ui.viewmodel.reservasi.DetailViewModelReservasi
import com.example.myapplication.ui.viewmodel.review.DetailUiStateReview
import com.example.myapplication.ui.viewmodel.review.DetailViewModelReview


object DestinasiDetailReview : DestinasiNavigasi {
    override val route = "detailReview"
    override val titleRes = "Detail Review"
    const val IdReview = "idreview"
    val routeWithArg = "$route/{$IdReview}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenReview(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModelReview = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit = { }
) {
    // Scaffold untuk mengatur tampilan atas dan FAB
    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailReview.titleRes,
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
            when (val state = viewModel.reviewUiState) {
                is DetailUiStateReview.Loading -> {
                    // Tampilkan indikator loading jika data sedang diambil
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStateReview.Error -> {
                    // Tampilkan pesan error jika terjadi kesalahan
                    Text("Error loading data", modifier = Modifier.align(Alignment.Start))
                }
                is DetailUiStateReview.Success -> {
                    ItemDetailReview (
                        review = state.review,
                        onDeleteClick = { review -> viewModel.deleteReview(review.id_review) },
                        onEditClick = { review -> onEditClick(review.id_review.toString()) },
                        navigateBack = navigateBack// Handle klik delete
                    )

                }

            }
        }
    }
}

@Composable
fun ItemDetailReview(
    modifier: Modifier = Modifier,
    review: Review,
    onDeleteClick: (Review) -> Unit, // Tambahkan properti onDeleteClick
    onEditClick: (Review) -> Unit,
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
                    text = review.nama_villa,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(review)
                    navigateBack()}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Review",
                        tint = MaterialTheme.colorScheme.error // Warna merah untuk delete
                    )
                }
                IconButton(onClick = {
                    onEditClick(review)
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Review",
                        tint = MaterialTheme.colorScheme.primary // Warna primary untuk edit
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailReview(judul = "Nilai", isinya = review.nilai)
            ComponentDetailReview(judul = "Tanggal Check in", isinya = review.komentar)
        }
    }
}


@Composable
fun ComponentDetailReview(
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