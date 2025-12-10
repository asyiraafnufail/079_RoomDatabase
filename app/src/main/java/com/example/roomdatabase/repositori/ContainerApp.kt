package com.example.roomdatabase.repositori

import android.app.Application
import android.content.Context
import com.example.roomdatabase.room.DatabaseSiswa

// 1. Interface ContainerApp
// Berfungsi sebagai "daftar isi" atau blueprint.
// Memberi tahu aplikasi dependensi apa saja yang tersedia (di sini hanya RepositoriSiswa).
interface ContainerApp{
    val repositoriSiswa : RepositoriSiswa
}

// 2. Class ContainerDataApp
// Berfungsi sebagai "pabrik" atau tempat pembuatan dependensi yang sebenarnya.
class ContainerDataApp(private val context: Context): ContainerApp {

    // 'by lazy' sangat PENTING:
    // Objek OfflineRepositoriSiswa (dan database) tidak akan dibuat saat aplikasi start,
    // tapi baru dibuat saat PERTAMA KALI data siswa diakses. Ini menghemat memori.
    override val repositoriSiswa: RepositoriSiswa by lazy {
        OfflineRepositoriSiswa(DatabaseSiswa.getDatabase(context).siswaDao())
    }
}

// 3. Class AplikasiSiswa
// Ini adalah titik awal aplikasi Android berjalan (sebelum masuk ke Activity manapun).
class AplikasiSiswa : Application(){

    // Variabel ini akan menyimpan instance ContainerApp agar bisa diakses
    // oleh seluruh Activity atau ViewModel di aplikasi.
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        // Saat aplikasi dibuka, kita siapkan containernya.
        // 'this' merujuk pada Application Context yang dibutuhkan oleh database.
        containerApp = ContainerDataApp(this)
    }
}