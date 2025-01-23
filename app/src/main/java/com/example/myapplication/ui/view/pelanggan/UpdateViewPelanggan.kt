package com.example.myapplication.ui.view.pelanggan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.villa.DestinasiUpdateVilla
import com.example.myapplication.ui.view.villa.EntryBody
import com.example.myapplication.ui.viewmodel.pelanggan.UpdateViewModelPelanggan
import com.example.myapplication.ui.viewmodel.villa.UpdateViewModelVilla
import kotlinx.coroutines.launch

object DestinasiUpdatePelanggan : DestinasiNavigasi {
    override val route = "updatePelanggan"
    override val titleRes = "Update Pelanggan"
    const val IdPelanggan = "idpelanggan"
    val routeWithArg = "$route/{$IdPelanggan}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenPelanggan(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateViewModelPelanggan = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertUiStatePelanggan = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePelanggan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            EntryBodyPelanggan(
                insertUiStatePelanggan = insertUiStatePelanggan,
                onPelangganValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onSaveClick = {
                    // Mengupdate data mahasiswa
                    coroutineScope.launch {
                        viewModel.updatePelanggan()  // Pastikan data diperbarui ke repository
                        onNavigate()  // Setelah update, navigasi ke halaman lain
                    }
                }
            )
        }
    }
}
