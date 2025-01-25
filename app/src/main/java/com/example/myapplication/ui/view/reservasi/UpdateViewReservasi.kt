package com.example.myapplication.ui.view.reservasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiEventReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiStateReservasi
import com.example.myapplication.ui.viewmodel.reservasi.UpdateViewModelReservasi
import kotlinx.coroutines.launch


object DestinasiUpdateReservasi : DestinasiNavigasi {
    override val route = "updateReservasi"
    const val IdReservasi= "idreservasi"
    val routeWithArg = "$route/{$IdReservasi}"
    override val titleRes = "Update Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateReservasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelReservasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiStateReservasi = viewModel.uiStateReservasi
    val villaList = viewModel.listVilla
    val pelangganList = viewModel.listPelanggan


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Form Body untuk Update Sesi Terapi
            EntryBodyReservasi(
                insertUiStateReservasi = uiStateReservasi,
                onReservasiValueChange =  { updatedValue ->
                    viewModel.updateReservasiState(updatedValue)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateReservasi()
                        navigateBack() // Navigate back after saving
                    }
                },
                villaList = villaList,
                pelangganList = pelangganList
            )
        }
    }
}

@Composable
fun EntryBodyReservasi(
    insertUiStateReservasi: InsertUiStateReservasi,
    onReservasiValueChange: (InsertUiEventReservasi) -> Unit,
    onSaveClick: () -> Unit,
    villaList: List<Villa>,
    pelangganList: List<Pelanggan>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
    ) {
        // Form Input untuk Sesi Terapi
        FormInputReservasi(
            insertUiEventReservasi = insertUiStateReservasi.insertUiEventReservasi,
            onValueChange = onReservasiValueChange,
            villaList = villaList,
            pelangganList = pelangganList,
            modifier = Modifier.fillMaxWidth()
        )

        // Button untuk Simpan Data
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}