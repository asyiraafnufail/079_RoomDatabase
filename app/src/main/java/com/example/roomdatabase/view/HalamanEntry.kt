package com.example.roomdatabase.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabase.viewmodel.DetailSiswa
import com.example.roomdatabase.viewmodel.EntryViewModel
import com.example.roomdatabase.viewmodel.UIStateSiswa
import com.example.roomdatabase.viewmodel.provider.PenyediaViewModel
import com.example.roomdatabase.view.route.DestinasiEntry
import com.example.roomdatabase.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    // Menghubungkan ke EntryViewModel untuk logika penyimpanan
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    // 1. Scroll Behavior
    // Efek animasi: Saat user scroll ke bawah, TopBar akan mengecil/hilang,
    // saat scroll ke atas, TopBar muncul lagi. Memberi ruang layar lebih luas.
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true, // Bisa kembali ke Home
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->

        // Memanggil body halaman (Formulir + Tombol Simpan)
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,
            // Setiap ketikan user langsung dikirim ke ViewModel untuk disimpan sementara
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                // Menggunakan coroutine karena penyimpanan ke DB bersifat asynchronous
                coroutineScope.launch {
                    viewModel.saveSiswa()
                    navigateBack() // Setelah simpan, kembali ke halaman sebelumnya
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // Agar bisa discroll jika keyboard muncul
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // 2. Formulir Input
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        // 3. Tombol Simpan
        Button(
            onClick = onSaveClick,
            // Fitur Validasi: Tombol hanya bisa diklik jika 'isEntryValid' bernilai true.
            // (Misal: Semua kolom harus terisi, jika tidak tombolnya abu-abu/mati).
            enabled = uiStateSiswa.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSiswa(
    detailSiswa: DetailSiswa,
    modifier: Modifier = Modifier,
    onValueChange: (DetailSiswa) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        // Input Nama
        OutlinedTextField(
            value = detailSiswa.nama,
            // Logic Copy: Kita menyalin objek lama, tapi mengganti bagian 'nama' saja dengan yang baru diketik.
            onValueChange = {onValueChange(detailSiswa.copy(nama=it)) },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Input Alamat
        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = {onValueChange(detailSiswa.copy(alamat=it))},
            label = { Text(stringResource(R.string.alamat)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        // Input Telepon
        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = {onValueChange(detailSiswa.copy(telpon = it))},
            // 4. Keyboard Type
            // Memastikan keyboard yang muncul hanya angka saja.
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = stringResource(R.string.telpon)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Teks info "Wajib Diisi"
        if (enabled) {
            Text(
                text = stringResource(R.string.required_field),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
        // Garis Pembatas Biru di bawah form
        HorizontalDivider(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
            thickness = dimensionResource(R.dimen.padding_small),
            color = Color.Blue
        )
    }
}