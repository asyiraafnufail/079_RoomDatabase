package com.example.roomdatabase.view.route

import com.example.roomdatabase.R

// Object ini adalah konfigurasi untuk halaman Detail Siswa.
// Dibuat 'object' agar statis dan bisa dipanggil langsung tanpa 'new'.
object DestinasiDetailSiswa : DestinasiNavigasi {

    // 1. Nama Rute Dasar
    // Ini seperti nama jalan utamanya.
    override val route = "detail_siswa"

    // 2. Judul Halaman
    // Teks yang akan muncul di TopAppBar (bagian atas layar).
    override val titleRes = R.string.detail_siswa

    // 3. Nama Argumen (Variabel)
    // Ini adalah kunci (key) untuk data yang akan dikirim.
    // Kita mengirim ID Siswa agar halaman detail tahu data siapa yang harus ditampilkan.
    const val itemIdArg = "idSiswa"

    // 4. Rute Lengkap dengan Argumen
    // Formatnya menjadi: "detail_siswa/{idSiswa}"
    // Tanda kurung kurawal {} memberi tahu sistem navigasi bahwa bagian ini
    // adalah data dinamis (berubah-ubah), bukan teks mati.
    // Contoh saat dijalankan nanti: "detail_siswa/10" atau "detail_siswa/25"
    val routeWithArgs = "$route/{$itemIdArg}"
}