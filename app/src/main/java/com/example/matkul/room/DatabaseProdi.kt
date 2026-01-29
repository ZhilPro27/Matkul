package com.example.matkul.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [ProgramStudi::class, MataKuliah::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseProdi : RoomDatabase() {
    abstract fun programStudiDao(): ProgramStudiDao
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object {
        @Volatile
        private var Instance: DatabaseProdi? = null

        fun getDatabase(context: Context): DatabaseProdi {
            return(Instance?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    DatabaseProdi::class.java,
                    "prodi_database"
                ).build().also { Instance = it }
            })
        }
    }
}