package com.example.matkul.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tblProgramStudi", indices = [Index(value = ["namaProgramStudi"], unique = true)])
data class ProgramStudi (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val namaProgramStudi : String,
)