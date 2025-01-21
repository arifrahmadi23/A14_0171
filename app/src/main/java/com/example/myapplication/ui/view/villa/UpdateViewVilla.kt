package com.example.myapplication.ui.view.villa

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
import com.example.myapplication.ui.viewmodel.villa.UpdateViewModelVilla
import kotlinx.coroutines.launch

object DestinasiUpdateVilla : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Villa"
    const val IdVilla = "idvilla"
    val routeWithArg = "$route/{$IdVilla}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenVilla(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateViewModelVilla = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertUiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateVilla.titleRes,
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
            EntryBody(
                insertUiState = insertUiState,
                onVillaValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onSaveClick = {
                    // Mengupdate data mahasiswa
                    coroutineScope.launch {
                        viewModel.updateVilla()  // Pastikan data diperbarui ke repository
                        onNavigate()  // Setelah update, navigasi ke halaman lain
                    }
                }
            )
        }
    }
}



