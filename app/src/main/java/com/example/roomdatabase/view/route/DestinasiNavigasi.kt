package com.example.roomdatabase.view.route

// Ini adalah Kontrak/Blueprint.
// Semua halaman (Screen) di aplikasi Anda WAJIB mematuhi aturan di interface ini.
interface DestinasiNavigasi{

    /**
     * Nama unik untuk menentukan jalur untuk composable
     * Contoh: "home", "entry_siswa", "detail_siswa/{id}"
     * Fungsinya seperti URL pada website.
     */
    val route: String

    /**
     * String resource id yang berisi judul yang akan ditampilkan di layar halaman
     * Tipe datanya 'Int' karena kita merujuk ke file strings.xml (R.string.judul_halaman).
     * Ini penting agar aplikasi bisa mendukung multi-bahasa (Localization).
     */
    val titleRes: Int
}