package com.example.roomdatabase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.repositori.RepositoriSiswa
import com.example.roomdatabase.view.route.DestinasiDetailSiswa
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriSiswa
) : ViewModel() {

    // 1. State UI (Mutable)
    // Berbeda dengan DetailViewModel yang pakai 'StateFlow' (Read-only stream),
    // di sini kita pakai 'mutableStateOf' karena data di layar Edit HARUS bisa diubah/diketik oleh user.
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // 2. Ambil ID
    // Sama seperti Detail, kita tangkap ID siswa dari argumen navigasi.
    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetailSiswa.itemIdArg])

    // 3. Inisialisasi Data (Load Data Awal)
    init {
        viewModelScope.launch {
            uiStateSiswa = repositoriSiswa.getSiswaStream(idSiswa)
                .filterNotNull()
                // PENTING: .first()
                // Kita hanya mengambil data PERTAMA kali saja (snapshot) untuk mengisi formulir.
                // Kita tidak butuh aliran data terus-menerus (Flow) karena user akan mengubah data ini di layar.
                .first()
                .toUiStateSiswa(true) // Konversi ke UI State, 'true' artinya data awal dianggap valid.
        }
    }

    // 4. Update Tampilan Saat Mengetik
    // Fungsi ini dipanggil setiap kali user mengetik satu huruf di TextField.
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            uiStateSiswa.copy(
                detailSiswa = detailSiswa,
                isEntryValid = validasiInput(detailSiswa) // Cek validasi real-time
            )
    }

    // 5. Validasi
    // Memastikan tidak ada kolom yang kosong saat diedit.
    private fun validasiInput(detailSiswa: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(detailSiswa) {
            nama.isNotBlank() &&
                    alamat.isNotBlank() &&
                    telpon.isNotBlank()
        }
    }

    // 6. Simpan Perubahan (Update)
    // Dipanggil saat tombol "Simpan" ditekan.
    suspend fun updateSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            // Panggil repository untuk melakukan perintah SQL UPDATE
            repositoriSiswa.updateSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}