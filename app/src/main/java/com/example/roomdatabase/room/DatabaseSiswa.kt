package com.example.roomdatabase.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Anotasi @Database
// Ini adalah konfigurasi utama.
// - entities: Daftar tabel yang ada di database (di sini hanya tabel Siswa).
// - version: Versi database. Jika Anda mengubah struktur tabel nanti, angkanya harus dinaikkan.
// - exportSchema: false agar tidak perlu menyimpan history skema database (biasanya untuk debugging).
@Database(entities = [Siswa::class], version = 1, exportSchema = false)
abstract class DatabaseSiswa : RoomDatabase(){

    // 2. Akses ke DAO
    // Fungsi abstrak ini nantinya akan diimplementasikan otomatis oleh Room.
    // Kita memanggil fungsi ini untuk mendapatkan akses ke perintah SQL (Insert, Delete, dll).
    abstract fun siswaDao(): SiswaDao

    // 3. Companion Object (Singleton)
    // Blok ini membuat database bersifat statis, artinya bisa diakses dari mana saja
    // tanpa perlu membuat objek DatabaseSiswa baru berulang kali.
    companion object{

        // @Volatile memastikan bahwa nilai variabel 'Instance'
        // segera terlihat oleh semua thread/proses lain jika ada perubahan.
        @Volatile
        private var Instance: DatabaseSiswa? = null

        // Fungsi ini yang dipanggil untuk mendapatkan database.
        fun getDatabase(context: Context): DatabaseSiswa{
            // Logika "Elvis Operator" ( ?: ):
            // Jika Instance sudah ada, kembalikan Instance tersebut.
            // Jika Instance masih null (belum dibuat), masuk ke blok synchronized.
            return (Instance ?: synchronized(this){

                // Room.databaseBuilder adalah kode inti yang membangun database fisik.
                Room.databaseBuilder(
                    context,
                    DatabaseSiswa::class.java,
                    "siswa_database" // Nama file database di dalam HP
                )
                    .build()
                    .also { Instance = it } // Simpan hasilnya ke variabel Instance
            })
        }
    }
}