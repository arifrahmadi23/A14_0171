package com.example.myapplication.ui.view.review

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.myapplication.model.Review
import com.example.myapplication.navigation.DestinasiNavigasi
import com.example.myapplication.ui.viewmodel.review.HomeReviewUiState
import com.example.myapplication.ui.viewmodel.review.HomeViewModelReview

object DestinasiHomeReview: DestinasiNavigasi {
    override val route = "homeReview"
    override val titleRes = "Home Review"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenReview(
    modifier: Modifier=Modifier,
    onDetailClick: (String) -> Unit ={},
    navigateBack: () -> Unit,
    viewModel: HomeViewModelReview = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeReview.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getReview()
                }
            )
        },

        ) { innerPadding->
        HomeStatusReview (
            homeReviewUiState =  viewModel.reviewUiState,
            retryAction = {viewModel.getReview()}, modifier = Modifier.padding(innerPadding),
            onDetailClick =  { idReview ->
                onDetailClick(idReview)
            }
        )
    }
}

@Composable
fun HomeStatusReview(
    homeReviewUiState: HomeReviewUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
){
    when (homeReviewUiState){
        is HomeReviewUiState.Loading-> OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is HomeReviewUiState.Success ->
            if(homeReviewUiState.review.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data review")
                }
            }else{
                ReviewLayout(
                    review = homeReviewUiState.review,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_review.toString())
                    },
                )
            }
        is HomeReviewUiState.Error -> OnError(
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
fun ReviewLayout(
    review: List<Review>,
    modifier: Modifier = Modifier,
    onDetailClick: (Review) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(review) { review ->
            ReviewCard(
                review = review,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(review) }
            )
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
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
                    text = review.id_review.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = review.nilai,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = review.komentar,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}