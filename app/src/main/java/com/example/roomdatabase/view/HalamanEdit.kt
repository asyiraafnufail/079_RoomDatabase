package com.example.roomdatabase.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabase.view.route.DestinasiEditSiswa
import com.example.roomdatabase.viewmodel.EditViewModel
import com.example.roomdatabase.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSiswaScreen(
    // Fungsi navigasi yang akan dipanggil setelah data berhasil disimpan (biasanya kembali ke list)
    navigateBack: () -> Unit,
    // Fungsi untuk tombol "Back" di pojok kiri atas (batal edit)
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    // Inisialisasi ViewModel khusus Edit.
    // Menggunakan 'PenyediaViewModel.Factory' agar ViewModel bisa mengakses Database/Repository.
    viewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // 1. Coroutine Scope
    // Diperlukan karena fungsi menyimpan ke database (viewModel.updateSiswa) adalah fungsi 'suspend'.
    // Kita butuh scope ini untuk menjalankannya di dalam onClick event.
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            // Menggunakan TopAppBar custom yang sudah kita bahas sebelumnya.
            // Judul diambil dari DestinasiEditSiswa ("Edit Siswa").
            SiswaTopAppBar(
                title = stringResource(DestinasiEditSiswa.titleRes),
                canNavigateBack = true, // Tampilkan panah back
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->

        // 2. EntrySiswaBody (Reuse Component)
        // Perhatikan ini: Kita menggunakan komponen tampilan yang SAMA dengan halaman Entry (Input Baru).
        // Bedanya, data (uiStateSiswa) di sini sudah berisi data siswa yang mau diedit.
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,

            // Saat user mengetik perubahan, update state di ViewModel
            onSiswaValueChange = viewModel::updateUiState,

            // Saat tombol Simpan diklik
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSiswa() // 1. Update data ke database
                    navigateBack()          // 2. Kembali ke halaman sebelumnya
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}