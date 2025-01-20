package com.example.myapplication.ui.viewvilla

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.model.Villa


@Composable
fun VillaLayout(
    villa: List<Villa>,
    modifier: Modifier = Modifier,
    onDetailClick:(Villa) -> Unit,
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(villa){
                villa ->
            VillaCard(
                villa = villa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(villa) },

            )
        }
    }
}

@Composable
fun VillaCard(
    villa: Villa,
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
                    text = villa.nama_villa,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = villa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = villa.kamar_tersedia + "Kamar Tesedia",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}