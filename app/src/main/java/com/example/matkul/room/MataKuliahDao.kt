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
interface MataKuliahDao {
    @Query("SELECT * FROM tblMataKuliah ORDER BY namaMataKuliah ASC")
    fun getAllMataKuliah() : Flow<List<MataKuliah>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM tblMataKuliah WHERE id = :id")
    fun getOneMataKuliah(id: Int) : Flow<MataKuliah>

    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    @Transaction
    @Query("SELECT * FROM tblMataKuliah WHERE idProgramStudi = :idProdi")
    fun getMataKuliahByProgramStudi(idProdi: Int) : Flow<List<MataKuliah>>

    @Query("""
    SELECT mk.*, ps.namaProgramStudi 
    FROM tblMataKuliah mk 
    JOIN tblProgramStudi ps ON mk.idProgramStudi = ps.id
""")
    fun getAllMataKuliahWithNamaProdi(): Flow<List<MataKuliahDetail>>
}