package com.example.matkul.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.matkul.repositori.AplikasiProdi
import com.example.matkul.viewmodel.ProgramStudiEditViewModel
import com.example.matkul.viewmodel.ProgramStudiEntryViewModel
import com.example.matkul.viewmodel.ProgramStudiViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            ProgramStudiViewModel(aplikasiProdi().container.repositoriProgramStudi)
        }

        initializer {
            ProgramStudiEntryViewModel(aplikasiProdi().container.repositoriProgramStudi)
        }

        initializer {
            ProgramStudiEditViewModel(
                this.createSavedStateHandle(),
                aplikasiProdi().container.repositoriProgramStudi)
        }
    }
}

fun CreationExtras.aplikasiProdi(): AplikasiProdi =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiProdi)
