package com.example.matkul.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramStudiDao {
    @Query("SELECT * FROM tblProgramStudi ORDER BY namaProgramStudi ASC")
    fun getAllProgramStudi() : Flow<List<ProgramStudi>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProgramStudi(programStudi: ProgramStudi)

    @Query("SELECT * FROM tblProgramStudi WHERE id = :id")
    fun getOneProgramStudi(id: Int) : Flow<ProgramStudi>

    @Update
    suspend fun updateProgramStudi(programStudi: ProgramStudi)

    @Delete
    suspend fun deleteProgramStudi(programStudi: ProgramStudi)

    @Transaction
    @Query("SELECT * FROM tblProgramStudi WHERE id = :idProgramStudi")
    fun getProgramStudiWithMataKuliah(idProgramStudi: Int): Flow<ProgramStudiWithMataKuliah>
}