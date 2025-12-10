package com.example.roomdatabase.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabase.R
import com.example.roomdatabase.room.Siswa
import com.example.roomdatabase.view.route.DestinasiHome
import com.example.roomdatabase.viewmodel.HomeViewModel
import com.example.roomdatabase.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,      // Navigasi ke halaman Tambah
    navigateToItemUpdate: (Int) -> Unit,  // Navigasi ke halaman Detail/Edit (bawa ID)
    modifier: Modifier = Modifier,
    // Inisialisasi ViewModel dengan Factory
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Menggunakan TopBar custom (tanpa tombol back)
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        // Tombol Tambah (+) di pojok kanan bawah
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        },
    ){ innerPadding ->

        // 1. Mengambil Data (State)
        // 'collectAsState' mengubah aliran data (Flow) dari database menjadi State Compose.
        // Artinya: Jika ada data baru di database, variabel 'uiStateSiswa' otomatis berubah,
        // dan layar akan refresh sendiri.
        val uiStateSiswa by viewModel.homeUiState.collectAsState()

        BodyHome(
            itemSiswa = uiStateSiswa.listSiswa,
            onSiswaClick = navigateToItemUpdate,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun BodyHome(
    itemSiswa: List<Siswa>,
    onSiswaClick: (Int) -> Unit,
    modifier: Modifier=Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        // 2. Logika Tampilan Kosong
        // Jika database kosong, tampilkan teks "Tidak ada data".
        if (itemSiswa.isEmpty()) {
            Text(
                text = stringResource(R.string.deskripsi_no_item),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            // Jika ada data, tampilkan ListSiswa
            ListSiswa(
                itemSiswa = itemSiswa,
                onSiswaClick = { onSiswaClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun ListSiswa(
    itemSiswa : List<Siswa>,
    onSiswaClick: (Siswa) -> Unit,
    modifier: Modifier=Modifier
){
    // 3. LazyColumn
    // Digunakan untuk menampilkan daftar data yang banyak.
    // 'Lazy' artinya hanya me-render item yang terlihat di layar saja (hemat memori).
    LazyColumn(modifier = modifier){
        items(items = itemSiswa, key = {it.id}){ person ->
            DataSiswa(
                siswa = person,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    // Saat kartu diklik, panggil fungsi onSiswaClick
                    .clickable { onSiswaClick(person) }
            )
        }
    }
}


@Composable
fun DataSiswa(
    siswa: Siswa,
    modifier: Modifier = Modifier
){
    // 4. Card (Desain Item)
    // Tampilan kotak untuk setiap satu data siswa
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge,
                )
                // Spacer weight(1f) mendorong elemen berikutnya (Icon & Telpon) ke ujung kanan
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Phone ,
                    contentDescription = null,
                )
                Text(
                    text = siswa.telpon,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}