package com.example.matkul.repositori

import com.example.matkul.room.ProgramStudi
import com.example.matkul.room.ProgramStudiDao
import com.example.matkul.room.ProgramStudiWithMataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoriProgramStudi{
    fun getAllProgramStudiStream(): Flow<List<ProgramStudi>>
    suspend fun insertProgramStudi(programStudi: ProgramStudi)
    fun getOneProgramStudiStream(id: Int): Flow<ProgramStudi?>
    suspend fun updateProgramStudi(programStudi: ProgramStudi)
    suspend fun deleteProgramStudi(programStudi: ProgramStudi)
    fun getProgramStudiWithMataKuliahStream(idProgramStudi: Int): Flow<ProgramStudiWithMataKuliah>
}

class OfflineRepositoriProgramStudi(
    private val programStudiDao: ProgramStudiDao
): RepositoriProgramStudi {
    override fun getAllProgramStudiStream(): Flow<List<ProgramStudi>> = programStudiDao.getAllProgramStudi()
    override suspend fun insertProgramStudi(programStudi: ProgramStudi) = programStudiDao.insertProgramStudi(programStudi)
    override fun getOneProgramStudiStream(id: Int): Flow<ProgramStudi?> = programStudiDao.getOneProgramStudi(id)
    override suspend fun updateProgramStudi(programStudi: ProgramStudi) = programStudiDao.updateProgramStudi(programStudi)
    override suspend fun deleteProgramStudi(programStudi: ProgramStudi) = programStudiDao.deleteProgramStudi(programStudi)
    override fun getProgramStudiWithMataKuliahStream(idProgramStudi: Int): Flow<ProgramStudiWithMataKuliah> = programStudiDao.getProgramStudiWithMataKuliah(idProgramStudi)
}