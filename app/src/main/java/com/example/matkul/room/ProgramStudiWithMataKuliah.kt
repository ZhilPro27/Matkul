package com.example.matkul.room

import androidx.room.Embedded
import androidx.room.Relation

data class ProgramStudiWithMataKuliah(
    @Embedded val programStudi: ProgramStudi,
    @Relation(
        parentColumn = "id",
        entityColumn = "idProgramStudi"
    )
    val daftarMataKuliah: List<MataKuliah>
)
