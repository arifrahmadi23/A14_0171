package com.example.myapplication.ui.view.reservasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.PenyediaViewModel
import com.example.myapplication.R
import com.example.myapplication.customwidget.CostumeTopAppBar
import com.example.myapplication.model.Pelanggan
import com.example.myapplication.model.Reservasi
import com.example.myapplication.model.Villa
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.view.pelanggan.DestinasiHomePelanggan
import com.example.myapplication.ui.view.pelanggan.HomeStatusPelanggan
import com.example.myapplication.ui.view.pelanggan.PelangganCard
import com.example.myapplication.ui.view.pelanggan.PelangganLayout
import com.example.myapplication.ui.viewmodel.pelanggan.HomePelangganUiState
import com.example.myapplication.ui.viewmodel.pelanggan.HomeViewModelPelanggan
import com.example.myapplication.ui.viewmodel.reservasi.HomeReservasiUiState
import com.example.myapplication.ui.viewmodel.reservasi.HomeViewModelReservasi

object DestinasiHomeReservasi: DestinasiNavigasi {
    override val route = "homeReservasi"
    override val titleRes = "Home Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenReservasi(
    modifier: Modifier=Modifier,
    onDetailClick: (String) -> Unit ={},
    navigateBack: () -> Unit,
    viewModel: HomeViewModelReservasi = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getReservasi()
                }
            )
        },

    ) { innerPadding->
        HomeStatusReservasi (
            homeReservasiUiState =  viewModel.reservasiUiState,
            retryAction = {viewModel.getReservasi()}, modifier = Modifier.padding(innerPadding),
            onDetailClick =  { idReservasi ->
                onDetailClick(idReservasi)
            }
        )
    }
}

@Composable
fun HomeStatusReservasi(
    homeReservasiUiState: HomeReservasiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
){
    when (homeReservasiUiState){
        is HomeReservasiUiState.Loading-> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is HomeReservasiUiState.Success ->
            if(homeReservasiUiState.reservasi.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data reservasi")
                }
            }else{
                ReservasiLayout(
                    reservasi = homeReservasiUiState.reservasi,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_reservasi.toString())
                    },
                )
            }
        is HomeReservasiUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction:() -> Unit, modifier: Modifier = Modifier){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ReservasiLayout(
    reservasi: List<Reservasi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Reservasi) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reservasi) { reservasi ->
            ReservasiCard(
                reservasi = reservasi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(reservasi) }
            )
        }
    }
}


@Composable
fun ReservasiCard(
    reservasi: Reservasi,
//    villa: Villa,
//    pelanggan: Pelanggan,

    modifier: Modifier = Modifier,
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reservasi.id_reservasi.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = reservasi.nama_villa,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = reservasi.nama_pelanggan,
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}
