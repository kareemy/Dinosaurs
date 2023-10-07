package com.example.dinosaurs.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dinosaurs.network.Dinosaur
import com.example.dinosaurs.network.DinosaursApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DinosaursUiState {
    data class Success(val dinosaurs: List<Dinosaur>) : DinosaursUiState
    object Error : DinosaursUiState
    object Loading: DinosaursUiState
}

class DinosaursViewModel : ViewModel() {
    var dinosaursUiState: DinosaursUiState by mutableStateOf(DinosaursUiState.Loading)
        private set

    init {
        getDinosaurs()
    }

    fun getDinosaurs() {
        viewModelScope.launch {
            dinosaursUiState = try {
                DinosaursUiState.Success(DinosaursApi.retrofitService.getDinosaurs())
            } catch (e: IOException) {
                DinosaursUiState.Error
            }
        }
    }
}