package com.example.myapplication.ui.view.review

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
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Villa
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.reservasi.DestinasiUpdateReservasi
import com.example.myapplication.ui.view.reservasi.EntryBodyReservasi
import com.example.myapplication.ui.view.reservasi.FormInputReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiEventReservasi
import com.example.myapplication.ui.viewmodel.reservasi.InsertUiStateReservasi
import com.example.myapplication.ui.viewmodel.reservasi.UpdateViewModelReservasi
import com.example.myapplication.ui.viewmodel.review.InsertUiEventReview
import com.example.myapplication.ui.viewmodel.review.InsertUiStateReview
import com.example.myapplication.ui.viewmodel.review.UpdateViewModelReview
import kotlinx.coroutines.launch

object DestinasiUpdateReview : DestinasiNavigasi {
    override val route = "updateReview"
    const val IdReview= "idreview"
    val routeWithArg = "$route/{$IdReview}"
    override val titleRes = "Update Review"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateReviewScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModelReview = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val uiStateReview = viewModel.uiStateReview
    val reservasiList = viewModel.listReservasi


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateReview.titleRes,
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
            EntryBodyReview(
                insertUiStateReview = uiStateReview,
                onReviewValueChange =  { updatedValue ->
                    viewModel.updateReviewState(updatedValue)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateReview()
                        navigateBack() // Navigate back after saving
                    }
                },
                reservasiList = reservasiList
            )
        }
    }
}




@Composable
fun EntryBodyReview(
    insertUiStateReview: InsertUiStateReview,
    onReviewValueChange: (InsertUiEventReview) -> Unit,
    onSaveClick: () -> Unit,
    reservasiList: List<Reservasi>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
    ) {
        // Form Input untuk Sesi Terapi
        FormInputReview(
            insertUiEventReview = insertUiStateReview.insertUiEventReview,
            onValueChange = onReviewValueChange,
            reservasiList = reservasiList,
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