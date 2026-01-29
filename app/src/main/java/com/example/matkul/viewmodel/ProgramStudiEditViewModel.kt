package com.example.matkul.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matkul.repositori.RepositoriProgramStudi
import com.example.matkul.view.route.DestinasiEditProgramStudi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProgramStudiEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriProgramStudi: RepositoriProgramStudi
): ViewModel(){
    var uiStateProgramStudi by mutableStateOf(UIStateProgramStudi())
        private set

    private val idProgramStudi: Int = checkNotNull(savedStateHandle[DestinasiEditProgramStudi.itemIdArg])
        init {
            viewModelScope.launch{
                uiStateProgramStudi = repositoriProgramStudi.getOneProgramStudiStream(idProgramStudi)
                    .filterNotNull()
                    .first()
                    .toUiStateProgramStudi(true)
            }
        }

    fun updateUiState(detailProgramStudi: DetailProgramStudi){
        uiStateProgramStudi =
            UIStateProgramStudi(detailProgramStudi = detailProgramStudi, isEntryValid = validasiInput(detailProgramStudi))
    }

    private fun validasiInput(uiState: DetailProgramStudi = uiStateProgramStudi.detailProgramStudi): Boolean {
        return with(uiState){
            namaProgramStudi.isNotBlank()
        }
    }

    suspend fun updateProgramStudi(){
        if(validasiInput(uiStateProgramStudi.detailProgramStudi)) {
            repositoriProgramStudi.updateProgramStudi(uiStateProgramStudi.detailProgramStudi.toProgramStudi())
        }
    }
}