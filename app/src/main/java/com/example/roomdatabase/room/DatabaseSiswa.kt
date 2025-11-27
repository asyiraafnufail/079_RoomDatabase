package com.example.roomdatabase.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Siswa::class], version = 1, exportSchema = false)
abstract class SiswaDatabase : RoomDatabase(){

}