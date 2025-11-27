package com.example.roomdatabase.repositori

import android.app.Application
import android.content.Context
import com.example.roomdatabase.room.DatabaseSiswa

interface ContainerApp{
    val repositoriSiswa : RepositoriSiswa
}

class ContainerDataApp(private val context: Context):
    ContainerApp{
    override val repositoriSiswa: RepositoriSiswa by lazy {
        OfflineRepositoriSiswa(DatabaseSiswa.getDatabase(context).siswaDao())
    }
}

class AplikasiSiswa : Application(){
    /**
     * AppContainer instance digunakan oleh kelas lainnya untuk mendapatkan dependensi
     */

    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerDataApp(this)
    }
}