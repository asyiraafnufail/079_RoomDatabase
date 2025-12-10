package com.example.roomdatabase.view.route

import com.example.roomdatabase.R

// Object ini adalah konfigurasi untuk halaman Utama (Home/Dashboard).
object DestinasiHome : DestinasiNavigasi {

    // 1. Nama Rute
    // Alamat unik untuk halaman utama.
    // NavHost akan menggunakan string "home" ini sebagai startDestination (titik awal).
    override val route = "home"

    // 2. Judul Halaman
    // Berbeda dengan halaman lain yang punya judul spesifik (seperti "Tambah Siswa"),
    // halaman utama biasanya menggunakan nama aplikasi (R.string.app_name) sebagai judul di TopAppBar.
    override val titleRes = R.string.app_name
}