package com.example.myapplication.ui.view.villa

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
import com.example.myapplication.ui.viewmodel.villa.FormErrorStateVilla
import com.example.myapplication.ui.viewmodel.villa.InsertUiEvent
import com.example.myapplication.ui.viewmodel.villa.InsertUiState
import com.example.myapplication.ui.viewmodel.villa.InsertViewModelVilla
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiEntryVilla : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryVillaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelVilla = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiStateVilla,
            onVillaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.valideteFieldsVilla()){
                        viewModel.insertVilla()
                        delay(500)
                        withContext(Dispatchers.Main){
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
fun EntryBody(
    insertUiState: InsertUiState,
    onVillaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputVilla(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onVillaValueChange,
            errorState = insertUiState.isEntryValid,
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
fun FormInputVilla(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    errorState: FormErrorStateVilla = FormErrorStateVilla(),
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.nama_villa,
            onValueChange = {onValueChange(insertUiEvent.copy(nama_villa = it))},
            label = { Text("Nama Villa") },
            isError = errorState.nama_villa != null,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = errorState.nama_villa ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.alamat,
            onValueChange = {onValueChange(insertUiEvent.copy(alamat = it))},
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertUiEvent.kamar_tersedia.toString(),
            onValueChange = { newValue ->
                val kamarTersediaInt = newValue.toIntOrNull() ?: 0 // Validasi dan konversi String ke Int
                onValueChange(insertUiEvent.copy(kamar_tersedia = kamarTersediaInt))
            },
            label = { Text("Jumlah Kamar") },
            isError = errorState.kamar_tersedia != null,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = errorState.kamar_tersedia ?: "",
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

