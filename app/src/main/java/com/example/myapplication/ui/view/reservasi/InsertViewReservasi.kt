package com.example.myapplication.ui.view.reservasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.customwidget.DropdownSelector
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Villa
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.pelanggan.DestinasiEntryPelanggan
import com.example.myapplication.ui.view.pelanggan.EntryBodyPelanggan
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiEventReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiStateReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertViewModelReservasi
import kotlinx.coroutines.launch


object DestinasiEntryReservasi : DestinasiNavigasi {
    override val route = "item_entryReservasi"
    override val titleRes = "Entry Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReservasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelReservasi = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val villaList = viewModel.listVilla
    val pelangganList = viewModel.listPelanggan

    remember {
        coroutineScope.launch {
            viewModel.loadExistingData()
        }
    }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyReservasi(
            insertUiStateReservasi = viewModel.uiStateReservasi,
            onValueChange = viewModel::updateInsertReservasiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.loadExistingData()
                    viewModel.insertReservasi()
                    navigateBack()
                }
            },
            villaList = villaList,
            pelangganList = pelangganList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }

}



@Composable
fun EntryBodyReservasi(
    insertUiStateReservasi: InsertUiStateReservasi,
    onValueChange: (InsertUiEventReservasi) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    villaList: List<Villa>,
    pelangganList: List<Pelanggan>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputReservasi(
            insertUiEventReservasi = insertUiStateReservasi.insertUiEventReservasi,
            onValueChange = onValueChange,
            villaList =  villaList,
            pelangganList = pelangganList,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInputReservasi(
    insertUiEventReservasi: InsertUiEventReservasi,
    onValueChange: (InsertUiEventReservasi) -> Unit,
    villaList: List<Villa>,
    pelangganList: List<Pelanggan>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
){
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)){
        DropdownSelector(
            label = "Pilih Villa",
            items = villaList.map { it.nama_villa },
            selectedItem = villaList.find { it.id_villa == insertUiEventReservasi.id_villa }?.nama_villa?:"",
            onItemSelected = {selected ->
                val selectedVilla = villaList.find { it.nama_villa == selected }
                selectedVilla?.let {
                    onValueChange(insertUiEventReservasi.copy(id_villa = it.id_villa))
                }
            }
        )

        DropdownSelector(
            label = "Pilih Pelanggan",
            items = pelangganList.map { it.nama_pelanggan },
            selectedItem = pelangganList.find { it.id_pelanggan == insertUiEventReservasi.id_pelanggan }?.nama_pelanggan?:"",
            onItemSelected = {selected ->
                val selectedPelanggan = pelangganList.find { it.nama_pelanggan == selected }
                selectedPelanggan?.let {
                    onValueChange(insertUiEventReservasi.copy(id_pelanggan = it.id_pelanggan))
                }
            }
        )

        OutlinedTextField(
            value = insertUiEventReservasi.check_in,
            onValueChange = {onValueChange(insertUiEventReservasi.copy(check_in = it))},
            label = { Text("Tanggal Check in") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertUiEventReservasi.check_out,
            onValueChange = {onValueChange(insertUiEventReservasi.copy(check_out = it))},
            label = { Text("Tanggal Check out") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertUiEventReservasi.jumlah_kamar.toString(),
            onValueChange = { newValue ->
                val jumlahKamarInt = newValue.toIntOrNull() ?: 0 // Validasi dan konversi String ke Int
                onValueChange(insertUiEventReservasi.copy(jumlah_kamar = jumlahKamarInt))
            },
            label = { Text("Jumlah Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


    }
}