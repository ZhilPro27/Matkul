package com.example.matkul.repositori

import android.app.Application
import android.content.Context
import com.example.matkul.room.DatabaseProdi

interface ContainerApp{
    val repositoriProgramStudi: RepositoriProgramStudi
    val repositoriMataKuliah: RepositoriMataKuliah
}

class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositoriProgramStudi: RepositoriProgramStudi by lazy{
        OfflineRepositoriProgramStudi(
            DatabaseProdi.getDatabase(context).programStudiDao()
        )
    }

    override val repositoriMataKuliah: RepositoriMataKuliah by lazy{
        OfflineRepositoriMataKuliah(
            DatabaseProdi.getDatabase(context).mataKuliahDao()
        )
    }
}

class AplikasiProdi : Application(){
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}