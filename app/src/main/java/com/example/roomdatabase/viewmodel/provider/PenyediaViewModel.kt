package com.example.roomdatabase.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roomdatabase.repositori.AplikasiSiswa
import com.example.roomdatabase.viewmodel.DetailViewModel
import com.example.roomdatabase.viewmodel.EditViewModel
import com.example.roomdatabase.viewmodel.EntryViewModel
import com.example.roomdatabase.viewmodel.HomeViewModel

object PenyediaViewModel {

    // 1. ViewModelFactory DSL
    // Blok ini mendefinisikan "Resep" bagaimana cara membuat setiap ViewModel.
    val Factory = viewModelFactory {

        // --- Resep untuk HomeViewModel ---
        initializer {
            // HomeViewModel butuh Repository untuk mengambil daftar siswa.
            // Kita ambil repository dari containerApp yang ada di class AplikasiSiswa.
            HomeViewModel(aplikasiSiswa().containerApp.repositoriSiswa)
        }

        // --- Resep untuk EntryViewModel ---
        initializer {
            // EntryViewModel butuh Repository untuk menyimpan (insert) siswa baru.
            EntryViewModel(aplikasiSiswa().containerApp.repositoriSiswa)
        }

        // --- Resep untuk DetailViewModel ---
        initializer {
            DetailViewModel(
                // createSavedStateHandle() PENTING:
                // Ini berfungsi untuk mengambil ARGUMEN NAVIGASI (ID Siswa)
                // yang dikirim dari halaman Home saat item diklik.
                this.createSavedStateHandle(),
                aplikasiSiswa().containerApp.repositoriSiswa
            )
        }

        // --- Resep untuk EditViewModel ---
        initializer {
            EditViewModel(
                // EditViewModel juga butuh SavedStateHandle untuk tahu
                // ID siswa mana yang sedang diedit.
                this.createSavedStateHandle(),
                aplikasiSiswa().containerApp.repositoriSiswa
            )
        }
    }
}

/**
 * Fungsi Ekstensi (Extension Function)
 * Berfungsi sebagai jalan pintas untuk mengakses objek 'AplikasiSiswa'.
 * * Tanpa fungsi ini, kodenya akan sangat panjang:
 * (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa).containerApp.repositoriSiswa
 */
fun CreationExtras.aplikasiSiswa(): AplikasiSiswa =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa)