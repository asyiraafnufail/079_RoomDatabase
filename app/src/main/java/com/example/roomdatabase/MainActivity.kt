package com.example.roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.roomdatabase.ui.theme.RoomDatabaseTheme
import com.example.roomdatabase.view.uicontroller.SiswaApp

// 1. ComponentActivity
// Ini adalah kelas dasar untuk aktivitas yang menggunakan Jetpack Compose.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Enable Edge to Edge
        // Membuat aplikasi tampil layar penuh (full screen) hingga ke belakang status bar (jam/baterai)
        // dan navigation bar (tombol home/back bawah), memberikan tampilan modern.
        enableEdgeToEdge()

        // 3. setContent
        // Ini adalah jembatan yang mengubah kode Kotlin Compose menjadi tampilan UI di layar.
        setContent {
            // 4. Theme
            // Membungkus aplikasi dengan tema warna dan tipografi yang sudah ditentukan.
            RoomDatabaseTheme {
                // Scaffold menyediakan struktur dasar layar.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // 5. Memanggil SiswaApp
                    // Di sinilah kita memanggil file navigasi utama (SiswaApp) yang sudah kita bahas sebelumnya.
                    // Modifier.padding(innerPadding) penting agar konten aplikasi tidak tertutup
                    // oleh status bar atau navigation bar HP.
                    SiswaApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}