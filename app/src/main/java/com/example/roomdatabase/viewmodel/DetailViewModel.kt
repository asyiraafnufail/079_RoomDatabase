package com.example.roomdatabase.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.repositori.RepositoriSiswa
import com.example.roomdatabase.view.route.DestinasiDetailSiswa
import com.example.roomdatabase.viewmodel.DetailSiswa
import com.example.roomdatabase.viewmodel.toDetailSiswa
import com.example.roomdatabase.viewmodel.toSiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailViewModel (
    // 1. SavedStateHandle
    // Ini adalah "kantong" penyimpanan argumen navigasi.
    // Saat kita navigasi "detail_siswa/10", angka 10 disimpan di sini.
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa
) : ViewModel(){

    // 2. Mengambil ID Siswa
    // Kita mengambil ID dari savedStateHandle menggunakan kunci 'itemIdArg'.
    // 'checkNotNull' memastikan aplikasi crash (fail-fast) jika ID-nya ternyata kosong (seharusnya tidak mungkin terjadi).
    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetailSiswa.itemIdArg])

    // 3. Pipeline Data (Stream)
    // Ini adalah aliran data otomatis.
    val uiDetailState: StateFlow<DetailSiswaUiState> =
        repositoriSiswa.getSiswaStream(idSiswa) // Ambil Flow<Siswa> dari Database
            .filterNotNull() // Pastikan datanya tidak null (jaga-jaga jika data baru saja dihapus)
            .map {
                // Konversi dari Entity (Siswa) ke UI State (DetailSiswaUiState)
                DetailSiswaUiState(detailSiswa = it.toDetailSiswa())
            }.stateIn(
                // Konversi Flow biasa (Cold) menjadi StateFlow (Hot) agar UI bisa memantaunya.
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailSiswaUiState()
            )

    // 4. Fungsi Hapus
    // Dipanggil saat tombol Delete ditekan di layar detail.
    suspend fun deleteSiswa(){
        // Konversi data UI kembali ke Entity Database, lalu hapus via repository.
        repositoriSiswa.deleteSiswa(uiDetailState.value.detailSiswa.toSiswa())
    }

    // Konstanta waktu tunggu 5 detik.
    // Jika aplikasi di-minimize (background), flow akan tetap aktif selama 5 detik
    // sebelum berhenti mengambil data untuk menghemat baterai.
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Wadah data untuk layar Detail.
 * Hanya berisi satu objek DetailSiswa.
 */
data class DetailSiswaUiState(
    val detailSiswa: DetailSiswa = DetailSiswa()
)
