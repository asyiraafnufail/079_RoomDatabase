package com.example.roomdatabase.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.roomdatabase.R

// 1. Anotasi ExperimentalMaterial3Api
// Diperlukan karena CenterAlignedTopAppBar masih dianggap fitur eksperimental
// oleh Google (bisa berubah di masa depan), tapi aman digunakan sekarang.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaTopAppBar(
    title: String,                  // Teks judul yang akan ditampilkan (misal: "Daftar Siswa")
    canNavigateBack: Boolean,       // Logika: True jika perlu tombol back, False jika tidak (misal di Home)
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null, // Untuk animasi saat di-scroll (opsional)
    navigateUp: () -> Unit = {}     // Fungsi yang dijalankan saat tombol back ditekan
) {
    // 2. CenterAlignedTopAppBar
    // Komponen Material 3 di mana judul otomatis berada di Tengan (Center).
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,

        // 3. Navigation Icon (Tombol Kiri)
        navigationIcon = {
            // Tombol back hanya muncul jika 'canNavigateBack' bernilai true.
            // Di halaman Home, ini akan false, jadi tidak ada tombol back.
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        // Menggunakan AutoMirrored agar arah panah menyesuaikan bahasa.
                        // (Kiri ke Kanan untuk Indo/Inggris, Kanan ke Kiri untuk Arab).
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}