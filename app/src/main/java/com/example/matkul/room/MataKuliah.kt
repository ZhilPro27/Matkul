package com.example.matkul.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "tblMataKuliah",
    foreignKeys = [
        ForeignKey(
            entity = ProgramStudi::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idProgramStudi"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class MataKuliah(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val namaMataKuliah :  String,
    val jumlahSKS : Int,
    val dosenPengampu : String,
    val tanggalUpdate : Long,
    val idProgramStudi: Int,
)

