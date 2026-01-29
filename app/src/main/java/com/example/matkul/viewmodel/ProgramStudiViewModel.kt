package com.example.matkul.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matkul.repositori.RepositoriProgramStudi
import com.example.matkul.room.ProgramStudi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgramStudiViewModel(private val repositoriProgramStudi: RepositoriProgramStudi): ViewModel(){
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val programStudiUiState: StateFlow<ProgramStudiUiState> = repositoriProgramStudi.getAllProgramStudiStream()
        .filterNotNull()
        .map{ ProgramStudiUiState(listProgramStudi = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProgramStudiUiState())

    data class ProgramStudiUiState(
        val listProgramStudi: List<ProgramStudi> = listOf()
    )
}