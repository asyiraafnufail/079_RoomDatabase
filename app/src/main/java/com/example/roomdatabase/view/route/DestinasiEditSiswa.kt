package com.example.roomdatabase.view.route

import com.example.roomdatabase.R

// Object ini adalah konfigurasi untuk halaman Edit Siswa.
object DestinasiEditSiswa : DestinasiNavigasi {

    // 1. Nama Rute
    // Alamat unik untuk layar edit.
    // Navigasi akan mencocokkan string ini untuk mengetahui harus membuka layar yang mana.
    override val route = "item_edit"

    // 2. Judul Halaman
    // Referensi ke string resource (res/values/strings.xml) yang bertuliskan "Edit Siswa".
    override val titleRes = R.string.edit_siswa

    // 3. Kunci Argumen (Variabel ID)
    // Sama seperti halaman detail, halaman edit WAJIB tahu ID siswa mana yang mau diedit.
    // Variabel ini "idSiswa" akan menjadi nama wadah untuk menampung angka ID tersebut.
    const val itemIdArg = "idSiswa"

    // 4. Rute Lengkap
    // Pola alamat: "item_edit/{idSiswa}"
    // Contoh nyata saat aplikasi jalan: "item_edit/5" (Artinya: Edit siswa dengan ID nomor 5).
    val routeWithArgs = "$route/{$itemIdArg}"
}