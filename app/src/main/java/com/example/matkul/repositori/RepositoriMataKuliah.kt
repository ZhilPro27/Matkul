package com.example.matkul.repositori

import com.example.matkul.room.MataKuliah
import com.example.matkul.room.MataKuliahDao
import com.example.matkul.room.MataKuliahDetail
import kotlinx.coroutines.flow.Flow

interface RepositoriMataKuliah {
    fun getAllMataKuliahStream(): Flow<List<MataKuliah>>
    suspend fun insertMataKuliah(MataKuliah: MataKuliah)
    fun getOneMataKuliahStream(id: Int): Flow<MataKuliah?>
    suspend fun updateMataKuliah(MataKuliah: MataKuliah)
    suspend fun deleteMataKuliah(MataKuliah: MataKuliah)
    fun getMataKuliahByProgramStudiStream(idProdi: Int): Flow<List<MataKuliah>>
    fun getAllMataKuliahWithNamaProdi(): Flow<List<MataKuliahDetail>>
}

class OfflineRepositoriMataKuliah(
    private val mataKuliahDao: MataKuliahDao
): RepositoriMataKuliah {
    override fun getAllMataKuliahStream(): Flow<List<MataKuliah>> = mataKuliahDao.getAllMataKuliah()
    override suspend fun insertMataKuliah(MataKuliah: MataKuliah) = mataKuliahDao.insertMataKuliah(MataKuliah)
    override fun getOneMataKuliahStream(id: Int): Flow<MataKuliah?> = mataKuliahDao.getOneMataKuliah(id)
    override suspend fun updateMataKuliah(MataKuliah: MataKuliah) = mataKuliahDao.updateMataKuliah(MataKuliah)
    override suspend fun deleteMataKuliah(MataKuliah: MataKuliah) = mataKuliahDao.deleteMataKuliah(MataKuliah)
    override fun getMataKuliahByProgramStudiStream(idProdi: Int): Flow<List<MataKuliah>> = mataKuliahDao.getMataKuliahByProgramStudi(idProdi)
    override fun getAllMataKuliahWithNamaProdi(): Flow<List<MataKuliahDetail>> = mataKuliahDao.getAllMataKuliahWithNamaProdi()
}