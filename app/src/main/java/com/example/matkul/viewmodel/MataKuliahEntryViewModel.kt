package com.example.matkul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matkul.repositori.RepositoriMataKuliah
import com.example.matkul.repositori.RepositoriProgramStudi
import com.example.matkul.room.MataKuliah
import com.example.matkul.room.ProgramStudi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.text.toIntOrNull

class MataKuliahEntryViewModel(
    private val repositoriMataKuliah: RepositoriMataKuliah,
    private val repositoriProgramStudi: RepositoriProgramStudi): ViewModel() {
    var uiStateMataKuliah by mutableStateOf(UIStateMataKuliah())
        private set

    val dataProgramStudi: StateFlow<List<ProgramStudi>> =
        repositoriProgramStudi.getAllProgramStudiStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )

    private fun validasiInput(uiState: DetailMataKuliah = uiStateMataKuliah.detailMataKuliah): Boolean {
        return with(uiState) {
            namaMataKuliah.isNotBlank() &&
                    jumlahSKS.isNotBlank() &&
                    jumlahSKS.all { it.isDigit() } &&
                    idProgramStudi.isNotBlank() &&
                    dosenPengampu.isNotBlank()
        }
    }

    fun updateUiState(detailMataKuliah: DetailMataKuliah){
        uiStateMataKuliah =
            UIStateMataKuliah(detailMataKuliah = detailMataKuliah, isEntryValid = validasiInput(detailMataKuliah))
    }

    suspend fun saveMataKuliah() {
        if (validasiInput()) {
            val mataKuliahEntity = uiStateMataKuliah.detailMataKuliah.toMataKuliah().copy(
                tanggalUpdate = System.currentTimeMillis()
            )

            repositoriMataKuliah.insertMataKuliah(mataKuliahEntity)
        }
    }
}

data class UIStateMataKuliah(
    val detailMataKuliah: DetailMataKuliah = DetailMataKuliah(),
    val isEntryValid: Boolean = false
)

data class DetailMataKuliah(
    val id: Int = 0,
    val namaMataKuliah: String = "",
    val jumlahSKS: String = "",
    val dosenPengampu: String = "",
    val idProgramStudi: String = "",
    val tanggalUpdate: Long = 0
)

fun DetailMataKuliah.toMataKuliah(): MataKuliah = MataKuliah(
    id = id,
    namaMataKuliah = namaMataKuliah,
    jumlahSKS = jumlahSKS.toIntOrNull() ?: 0,
    dosenPengampu = dosenPengampu,
    idProgramStudi = idProgramStudi.toIntOrNull() ?: 0,
    tanggalUpdate = System.currentTimeMillis()
)

fun MataKuliah.toUiStateMataKuliah(isEntryValid: Boolean = false): UIStateMataKuliah = UIStateMataKuliah(
    detailMataKuliah = this.toDetailMataKuliah(),
    isEntryValid =  isEntryValid
)

fun MataKuliah.toDetailMataKuliah(): DetailMataKuliah = DetailMataKuliah(
    id = id,
    namaMataKuliah = namaMataKuliah,
    jumlahSKS = jumlahSKS.toString(),
    dosenPengampu = dosenPengampu,
    idProgramStudi = idProgramStudi.toString(),
    tanggalUpdate = tanggalUpdate
)