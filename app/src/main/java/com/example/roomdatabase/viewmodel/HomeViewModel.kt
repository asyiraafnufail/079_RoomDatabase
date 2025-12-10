package com.example.roomdatabase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.repositori.RepositoriSiswa
import com.example.roomdatabase.room.Siswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(private val repositoriSiswa: RepositoriSiswa): ViewModel() {

    // Konstanta untuk menjaga koneksi flow tetap hidup selama 5 detik
    // setelah aplikasi masuk ke background (atau saat rotasi layar).
    // Ini mencegah pengambilan data ulang (restart query) yang tidak perlu saat konfigurasi berubah.
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // Pipeline Data Utama
    // Mengubah Flow<List<Siswa>> dari Repository menjadi StateFlow<HomeUiState> yang siap pakai oleh UI.
    val homeUiState: StateFlow<HomeUiState> = repositoriSiswa.getAllSiswaStream()
        .filterNotNull() // Memastikan tidak memproses data null
        .map {
            // Setiap kali database berubah, ambil list barunya,
            // lalu bungkus ke dalam objek HomeUiState.
            HomeUiState(listSiswa = it.toList())
        }
        .stateIn(
            scope = viewModelScope, // Flow ini hidup selama ViewModel hidup
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), // Strategi hemat resource
            initialValue = HomeUiState() // Data awal sebelum database selesai loading (List kosong)
        )

    // Wadah data untuk Home Screen
    // Sengaja dibuat data class terpisah agar fleksibel jika nanti
    // mau menambah info lain (misal: jumlah siswa, status loading, dll)
    data class HomeUiState(
        val listSiswa: List<Siswa> = listOf()
    )
}