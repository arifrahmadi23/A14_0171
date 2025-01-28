package com.example.myapplication.ui.view.pelanggan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.villa.DestinasiEntryVilla
import com.example.myapplication.ui.view.villa.EntryBody
import com.example.myapplication.ui.view.villa.FormInputVilla
import com.example.myapplication.ui.viewmodel.pelanggan.FormErrorStatePelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.InsertUiEventPelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.InsertUiStatePelanggan
import com.example.myapplication.ui.viewmodel.pelanggan.InsertViewModelPelanggan
import com.example.myapplication.ui.viewmodel.villa.InsertUiEvent
import com.example.myapplication.ui.viewmodel.villa.InsertUiState
import com.example.myapplication.ui.viewmodel.villa.InsertViewModelVilla
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiEntryPelanggan : DestinasiNavigasi {
    override val route = "item_entryPelanggan"
    override val titleRes = "Entry Pealnggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPelangganScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelPelanggan = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPelanggan(
            insertUiStatePelanggan = viewModel.uiStatePelanggan,
            onPelangganValueChange = viewModel::updateInsertPelangganState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFieldsPelanggan()) {
                        viewModel.insertPelanggan()
                        delay(500)
                        withContext(Dispatchers.Main) {
                            navigateBack()
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPelanggan(
    insertUiStatePelanggan: InsertUiStatePelanggan,
    onPelangganValueChange: (InsertUiEventPelanggan) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPelanggan(
            insertUiEventPelanggan = insertUiStatePelanggan.insertUiEventPelanggan,
            onValueChange = onPelangganValueChange,
            errorState = insertUiStatePelanggan.isEntryValid,
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
fun FormInputPelanggan(
    insertUiEventPelanggan: InsertUiEventPelanggan,
    modifier: Modifier = Modifier,
    errorState: FormErrorStatePelanggan = FormErrorStatePelanggan(),
    onValueChange: (InsertUiEventPelanggan) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEventPelanggan.nama_pelanggan,
            onValueChange = {onValueChange(insertUiEventPelanggan.copy(nama_pelanggan = it))},
            label = { Text("Nama Pelanggan") },
            isError = errorState.nama_pelanggan != null,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = errorState.nama_pelanggan ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEventPelanggan.no_hp,
            onValueChange = {onValueChange(insertUiEventPelanggan.copy(no_hp = it))},
            label = { Text("Nomor Handphone") },
            isError = errorState.no_hp != null ,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = errorState.no_hp ?: "",
            color = Color.Red
        )

        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )

    }
}