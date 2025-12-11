package com.example.roomdatabase.view.route

import com.example.roomdatabase.R

// Object ini adalah konfigurasi untuk halaman Input/Entry data baru.
object DestinasiEntry : DestinasiNavigasi{

    // 1. Nama Rute
    // Alamat untuk membuka halaman formulir kosong.
    override val route = "item_entry"

    // 2. Judul Halaman
    // Teks judul di bagian atas layar (misal: "Tambah Siswa").
    override val titleRes = R.string.entry_siswa
}