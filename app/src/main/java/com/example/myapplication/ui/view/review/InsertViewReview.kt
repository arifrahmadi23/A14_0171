package com.example.myapplication.ui.view.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.customwidget.DropdownSelector
import com.example.myapplication.model.Reservasi
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.reservasi.DestinasiEntryReservasi
import com.example.myapplication.ui.view.reservasi.EntryBodyReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiEventReservasi
import com.example.myapplication.ui.viewmodel.review.InsertUiEventReview
import com.example.myapplication.ui.viewmodel.review.InsertUiStateReview
import com.example.myapplication.ui.viewmodel.review.InsertViewModelReview
import kotlinx.coroutines.launch

object DestinasiEntryReview : DestinasiNavigasi {
    override val route = "item_entryReview"
    override val titleRes = "Entry Review"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReviewScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModelReview = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val reservasiList = viewModel.listReservasi

    remember {
        coroutineScope.launch {
            viewModel.loadExistingData()
        }
    }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryReview.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyReview(
            insertUiStateReview = viewModel.uiStateReview,
            onValueChange = viewModel::updateInsertReviewState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.loadExistingData()
                    viewModel.insertReview()
                    navigateBack()
                }
            },
            reservasiList = reservasiList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyReview(
    insertUiStateReview: InsertUiStateReview,
    onValueChange: (InsertUiEventReview) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier,
    reservasiList: List<Reservasi>
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputReview(
            insertUiEventReview = insertUiStateReview.insertUiEventReview,
            onValueChange = onValueChange,
            reservasiList = reservasiList,
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
fun FormInputReview(
    insertUiEventReview: InsertUiEventReview,
    onValueChange: (InsertUiEventReview) -> Unit,
    reservasiList: List<Reservasi>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val scrollState = rememberScrollState()
    val nilaiOptions = listOf("Sangat Puas", "Puas", "Biasa", "Tidak Puas", "Sangat Tidak Puas")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max) // atau nilai tinggi eksplisit
            .verticalScroll(scrollState)
    ) {

        // Dropdown for Reservasi
        DropdownSelector(
            label = "Pilih Reservasi",
            items = reservasiList.map { it.id_reservasi.toString() },
            selectedItem = reservasiList.find { it.id_reservasi == insertUiEventReview.id_reservasi }?.id_reservasi.toString() ?: "",
            onItemSelected = { selected ->
                val selectedReservasi = reservasiList.find { it.id_reservasi.toString() == selected }
                selectedReservasi?.let {
                    onValueChange(insertUiEventReview.copy(id_reservasi = it.id_reservasi, nama_villa = it.nama_villa))
                }
            }
        )

        // Display Nama Villa (Read-only)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertUiEventReview.nama_villa,
            onValueChange = {},
            label = { Text("Nama Villa") },
            enabled = false // Read-only field
        )

        // Radio Buttons for Nilai
        Text(text = "Nilai", style = MaterialTheme.typography.bodyLarge)

        Column(modifier = Modifier.fillMaxWidth()) {
            nilaiOptions.forEach { option ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = insertUiEventReview.nilai == option,
                        onClick = { onValueChange(insertUiEventReview.copy(nilai = option)) }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Komentar Input
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertUiEventReview.komentar,
            onValueChange = { onValueChange(insertUiEventReview.copy(komentar = it)) },
            label = { Text("Komentar") },
            enabled = enabled
        )

        if (enabled) {
            Text(
                text = "Pastikan semua data terisi dengan benar!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Divider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}
