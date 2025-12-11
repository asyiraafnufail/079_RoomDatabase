package com.example.roomdatabase.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.roomdatabase.repositori.RepositoriSiswa
import com.example.roomdatabase.room.Siswa
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class EntryViewModel(private val repositoriSiswa: RepositoriSiswa): ViewModel(){

    // 1. State Formulir
    // Menggunakan mutableStateOf agar UI (TextField) bisa diperbarui real-time saat user mengetik.
    // Default-nya kosong (UIStateSiswa()).
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // 2. Validasi Sederhana
    // Mengecek apakah kolom nama, alamat, dan telepon sudah diisi.
    // Jika ada satu saja yang kosong (blank), return false.
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    // 3. Update State
    // Fungsi ini dipanggil setiap user mengetik satu huruf.
    // Ia memperbarui data siswa sekaligus mengecek validitasnya (untuk mengaktifkan/mematikan tombol Simpan).
    fun updateUiState(detailSiswa: DetailSiswa){
        uiStateSiswa = UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    // 4. Simpan ke Database
    // Fungsi suspend untuk operasi IO (Input/Output) ke database.
    suspend fun saveSiswa(){
        if (validasiInput()){
            // Sebelum disimpan, data UI (DetailSiswa) dikonversi dulu menjadi Entity Database (Siswa)
            // menggunakan fungsi .toSiswa()
            repositoriSiswa.insertSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}
// A. Wrapper untuk Layar (Mencakup Data + Status Validasi)
data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false // Status tombol simpan (Aktif/Mati)
)

// B. Wadah Data Murni (Mirip Entity tapi tanpa anotasi @Entity)
// Inilah yang dimanipulasi saat user mengetik di form.
data class DetailSiswa(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = ""
)

// --- FUNGSI PENERJEMAH (EXTENSION FUNCTIONS) ---

// 1. UI -> Database (Dipakai saat Save/Insert/Update)
// Mengubah data form menjadi data siap simpan ke database.
fun DetailSiswa.toSiswa(): Siswa = Siswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)

// 2. Database -> UI (Dipakai saat Load Edit/Detail)
// Mengubah data dari database agar bisa ditampilkan di form.
fun Siswa.toUiStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(), // Panggil fungsi di bawah
    isEntryValid = isEntryValid
)

// 3. Helper untuk no. 2
fun Siswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon
)