package com.example.matkul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matkul.repositori.RepositoriMataKuliah
import com.example.matkul.repositori.RepositoriProgramStudi
import com.example.matkul.room.ProgramStudi
import com.example.matkul.view.route.DestinasiEditMataKuliah
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MataKuliahEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriMataKuliah: RepositoriMataKuliah,
    private val repositoriProgramStudi: RepositoriProgramStudi
): ViewModel() {

    // 1. Inisialisasi State UI
    var uiStateMataKuliah by mutableStateOf(UIStateMataKuliah())
        private set

    // 2. Aliran data Program Studi untuk Dropdown
    val dataProgramStudi: StateFlow<List<ProgramStudi>> =
        repositoriProgramStudi.getAllProgramStudiStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )

    // 3. Mengambil ID dari SavedStateHandle (Pastikan key sesuai dengan Navigasi)
    private val idMataKuliah: Int = checkNotNull(savedStateHandle[DestinasiEditMataKuliah.itemIdArg])

    // 4. Mengambil data lama saat halaman dibuka
    init {
        viewModelScope.launch {
            uiStateMataKuliah = repositoriMataKuliah.getOneMataKuliahStream(idMataKuliah)
                .filterNotNull()
                .first()
                .toUiStateMataKuliah(isEntryValid = true) // Set valid true karena data lama pasti valid
        }
    }

    // 5. Fungsi Update UI State saat user mengetik
    fun updateUiState(detailMataKuliah: DetailMataKuliah) {
        uiStateMataKuliah = UIStateMataKuliah(
            detailMataKuliah = detailMataKuliah,
            isEntryValid = validasiInput(detailMataKuliah)
        )
    }

    // 6. Fungsi Simpan Perubahan (Update)
    suspend fun updateMataKuliah() {
        if (validasiInput()) {
            // Konversi ke entity dan paksa update timestamp ke waktu sekarang
            val mataKuliahEntity = uiStateMataKuliah.detailMataKuliah.toMataKuliah().copy(
                tanggalUpdate = System.currentTimeMillis()
            )
            repositoriMataKuliah.updateMataKuliah(mataKuliahEntity)
        }
    }

    // 7. Fungsi Hapus
    suspend fun deleteMataKuliah() {
        // Pastikan kita mengonversi data saat ini ke entity untuk dihapus
        repositoriMataKuliah.deleteMataKuliah(uiStateMataKuliah.detailMataKuliah.toMataKuliah())
    }

    // 8. Logika Validasi (Pastikan SKS angka dan Prodi terpilih)
    private fun validasiInput(uiState: DetailMataKuliah = uiStateMataKuliah.detailMataKuliah): Boolean {
        return with(uiState) {
            namaMataKuliah.isNotBlank() &&
                    jumlahSKS.isNotBlank() &&
                    jumlahSKS.all { it.isDigit() } &&
                    idProgramStudi.isNotBlank() &&
                    idProgramStudi != "0" && // Tambahan: pastikan bukan ID default
                    dosenPengampu.isNotBlank()
        }
    }
}