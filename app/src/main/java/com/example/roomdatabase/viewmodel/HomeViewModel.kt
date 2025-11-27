package com.example.roomdatabase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.roomdatabase.repositori.RepositoriSiswa

class HomeViewModel(private val repositoriSiswa: RepositoriSiswa): ViewModel(){
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L

    }
}