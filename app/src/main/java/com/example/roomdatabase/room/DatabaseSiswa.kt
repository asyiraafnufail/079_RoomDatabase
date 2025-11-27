package com.example.roomdatabase.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Siswa::class], version = 1, exportSchema = false)
abstract class SiswaDatabase : RoomDatabase(){
    abstract fun siswaDao(): SiswaDao

    companion object{
        @Volatile
        private var INSTANCE: SiswaDatabase? = null

        fun getDatabase(context: Context): SiswaDatabase{
    }
}