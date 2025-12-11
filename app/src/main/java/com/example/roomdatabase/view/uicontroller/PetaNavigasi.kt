package com.example.roomdatabase.view.uicontroller

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
// Import screen dan route yang sudah dibuat sebelumnya
import com.example.roomdatabase.view.DetailSiswaScreen
import com.example.roomdatabase.view.EditSiswaScreen
import com.example.roomdatabase.view.EntrySiswaScreen
import com.example.roomdatabase.view.HomeScreen
import com.example.roomdatabase.view.route.DestinasiDetailSiswa
import com.example.roomdatabase.view.route.DestinasiDetailSiswa.itemIdArg
import com.example.roomdatabase.view.route.DestinasiEditSiswa
import com.example.roomdatabase.view.route.DestinasiHome
import com.example.roomdatabase.view.route.DestinasiEntry

// 1. SiswaApp
// Ini adalah pembungkus utama aplikasi.
// Fungsinya membuat kontroler navigasi (rememberNavController)
// dan memanggil HostNavigasi.
@Composable
fun SiswaApp(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier){
    HostNavigasi(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    // 2. NavHost
    // Ini adalah "Wadah" di mana layar akan berganti-ganti.
    // startDestination = Menentukan layar apa yang pertama kali muncul (Home).
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    )
    {
        // --- Rute ke Halaman Home ---
        composable(DestinasiHome.route){
            HomeScreen(
                // Jika tombol tambah ditekan, navigasi ke halaman Entry
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },

                // Jika salah satu siswa di list diklik:
                // Kita navigasi ke halaman Detail sambil membawa ID siswa tersebut ($it).
                // Contoh URL: "detail_siswa/5"
                navigateToItemUpdate = {
                    navController.navigate("${DestinasiDetailSiswa.route}/$it")
                }
            )
        }

        // --- Rute ke Halaman Entry (Tambah Data) ---
        composable(DestinasiEntry.route){
            EntrySiswaScreen(
                // Jika tombol back ditekan, kembali ke stack sebelumnya (Home)
                navigateBack = { navController.popBackStack() }
            )
        }

        // --- Rute ke Halaman Detail (Lihat Data) ---
        // Rute ini spesial karena menerima ARGUMEN (itemIdArg).
        composable(
            route = DestinasiDetailSiswa.routeWithArgs,
            arguments = listOf(navArgument(itemIdArg) {
                type = NavType.IntType // Memastikan ID yang diterima harus Angka (Int)
            })
        ){
            DetailSiswaScreen(
                // Dari halaman detail, jika mau edit, lanjut ke halaman Edit
                // Sambil mengoper ID yang sama ($it)
                navigateToEditItem = { navController.navigate("${DestinasiEditSiswa.route}/$it") },

                // Kembali ke halaman sebelumnya
                navigateBack = { navController.navigateUp() }
            )
        }

        // --- Rute ke Halaman Edit (Ubah Data) ---
        // Sama seperti Detail, halaman ini juga butuh ID untuk tahu siapa yang diedit.
        composable(
            route = DestinasiEditSiswa.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEditSiswa.itemIdArg) {
                    type = NavType.IntType
                }
            )
        ) {
            EditSiswaScreen(
                // Setelah selesai edit atau batal, kembali ke halaman sebelumnya
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}