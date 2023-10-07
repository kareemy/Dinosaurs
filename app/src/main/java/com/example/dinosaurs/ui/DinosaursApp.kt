package com.example.dinosaurs.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dinosaurs.R

@Composable
fun DinosaursApp() {

    val dinosaursViewModel: DinosaursViewModel = viewModel()
    Scaffold(
        topBar = { DinosaursTopAppBar() }
    ) {
        HomeScreen(
            contentPadding = it,
            retryAction = dinosaursViewModel::getDinosaurs,
            dinosaursUiState = dinosaursViewModel.dinosaursUiState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DinosaursTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        modifier = modifier
    )
}