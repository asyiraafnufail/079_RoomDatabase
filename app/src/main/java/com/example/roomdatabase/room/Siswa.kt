package com.example.roomdatabase.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. Anotasi @Entity
// Memberi tahu Room bahwa class ini bukan class biasa, tapi merupakan skema TABEL database.
// 'tableName = "tblSiswa"' berguna jika Anda ingin nama tabel di database beda dengan nama class-nya.
// (Jika tidak ditulis, nama tabel otomatis jadi "Siswa").
@Entity(tableName = "tblSiswa")
data class Siswa(

    // 2. Primary Key
    // Setiap tabel WAJIB punya satu kolom unik sebagai identitas.
    // 'autoGenerate = true' artinya ID akan dibuat otomatis berurutan (1, 2, 3, dst).
    // Anda tidak perlu memikirkan nomor urutnya saat input data.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Nilai default 0. Room akan mengabaikan angka 0 ini dan menggantinya dengan ID baru.

    // 3. Kolom Data
    // Variabel di bawah ini akan otomatis menjadi kolom-kolom di tabel "tblSiswa".
    val nama: String,
    val alamat: String,
    val telpon: String
)