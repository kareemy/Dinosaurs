package com.example.dinosaurs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dinosaurs.R
import com.example.dinosaurs.network.Dinosaur
import com.example.dinosaurs.ui.theme.DinosaursTheme

@Composable
fun HomeScreen(
    dinosaursUiState: DinosaursUiState,
    retryAction: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    when (dinosaursUiState) {
        is DinosaursUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is DinosaursUiState.Success ->
            DinosaurList(
                dinosaurs = dinosaursUiState.dinosaurs,
                contentPadding = contentPadding
            )
        is DinosaursUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun DinosaurList(
    dinosaurs: List<Dinosaur>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dinosaurs) { dinosaur ->
            DinosaurCard(
                dinosaur = dinosaur,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun DinosaurCard(dinosaur: Dinosaur, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.dinosaur_title, dinosaur.name, dinosaur.length),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(dinosaur.imgSrc)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = dinosaur.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = dinosaur.description,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun DinosaurCardPreview() {
    DinosaursTheme {
        val mockData = Dinosaur(
            name = "Tyrannosaurus rex",
            length = "40 feet long",
            description = "The Tyrannosaurus rex, often abbreviated as T. rex, was a fearsome carnivorous dinosaur that lived during the Late Cretaceous period, about 68 to 66 million years ago. It was one of the largest land predators with massive jaws filled with sharp teeth and tiny, non-functional arms.",
            imgSrc = ""
        )
        DinosaurCard(dinosaur = mockData)
    }
}

@Preview
@Composable
fun DinosaurListPreview() {
    DinosaursTheme {
        val mockData = List(10) {
            Dinosaur(
                name = "Tyrannosaurs rex",
                length = "40 feet long",
                description = "The Tyrannosaurus rex, often abbreviated as T. rex, was a fearsome carnivorous dinosaur that lived during the Late Cretaceous period, about 68 to 66 million years ago. It was one of the largest land predators with massive jaws filled with sharp teeth and tiny, non-functional arms.",
                imgSrc = ""
            )
        }
        DinosaurList(dinosaurs = mockData)
    }
}