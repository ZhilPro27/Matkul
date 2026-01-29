package com.example.matkul.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.matkul.repositori.RepositoriProgramStudi
import com.example.matkul.room.ProgramStudi

class ProgramStudiEntryViewModel(private val repositoriProgramStudi: RepositoriProgramStudi): ViewModel() {
    var uiStateProgramStudi by mutableStateOf(UIStateProgramStudi())
        private set

    private fun validasiInput(uiState: DetailProgramStudi = uiStateProgramStudi.detailProgramStudi): Boolean{
        return with(uiState) {
            namaProgramStudi.isNotBlank()
        }
    }

    fun updateUiState(detailProgramStudi: DetailProgramStudi){
        uiStateProgramStudi =
            UIStateProgramStudi(detailProgramStudi = detailProgramStudi, isEntryValid = validasiInput(detailProgramStudi))
    }

    suspend fun saveProgramStudi() {
        if(validasiInput()) {
            repositoriProgramStudi.insertProgramStudi(uiStateProgramStudi.detailProgramStudi.toProgramStudi())
        }
    }
}

data class UIStateProgramStudi(
    val detailProgramStudi: DetailProgramStudi = DetailProgramStudi(),
    val isEntryValid: Boolean = false
)

data class DetailProgramStudi(
    val id: Int = 0,
    val namaProgramStudi: String = ""
)

fun DetailProgramStudi.toProgramStudi(): ProgramStudi = ProgramStudi(
    id = id,
    namaProgramStudi = namaProgramStudi
)

fun ProgramStudi.toUiStateProgramStudi(isEntryValid: Boolean = false): UIStateProgramStudi = UIStateProgramStudi(
    detailProgramStudi = this.toDetailProgramStudi(),
    isEntryValid =  isEntryValid
)

fun ProgramStudi.toDetailProgramStudi(): DetailProgramStudi = DetailProgramStudi(
    id = id,
    namaProgramStudi = namaProgramStudi
)