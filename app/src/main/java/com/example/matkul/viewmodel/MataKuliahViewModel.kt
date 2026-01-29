package com.example.matkul.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matkul.repositori.RepositoriMataKuliah
import com.example.matkul.room.MataKuliahDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MataKuliahViewModel(private val repositoriMataKuliah: RepositoriMataKuliah): ViewModel(){
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val mataKuliahUiState: StateFlow<MataKuliahUiState> = repositoriMataKuliah.getAllMataKuliahWithNamaProdi()
        .filterNotNull()
        .map{ MataKuliahUiState(listMataKuliah = it.toList()) }
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = MataKuliahUiState())

    data class MataKuliahUiState(
        val listMataKuliah: List<MataKuliahDetail> = listOf()
    )
}