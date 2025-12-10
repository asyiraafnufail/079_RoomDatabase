package com.example.roomdatabase.repositori

import com.example.roomdatabase.room.Siswa
import com.example.roomdatabase.room.SiswaDao
import kotlinx.coroutines.flow.Flow

// 1. Interface RepositoriSiswa
// Ini adalah "kontrak" atau menu yang tersedia.
// Interface ini menentukan operasi apa saja yang BISA dilakukan terhadap data siswa,
// tanpa peduli datanya datang dari database lokal atau internet.
interface RepositoriSiswa{

    // Menggunakan 'Flow' artinya memberikan aliran data secara real-time.
    // Jika ada perubahan di database, UI akan otomatis diperbarui tanpa perlu refresh manual.
    fun getAllSiswaStream(): Flow<List<Siswa>>

    // Kata kunci 'suspend' artinya fungsi ini berjalan di background (asynchronous).
    // Operasi berat (Insert, Delete, Update) harus pakai suspend agar aplikasi tidak macet (not responding).
    suspend fun insertSiswa(siswa: Siswa)

    // Mengambil satu data siswa spesifik, juga secara real-time (Flow).
    fun getSiswaStream(id: Int): Flow<Siswa?>

    suspend fun deleteSiswa(siswa: Siswa)

    suspend fun updateSiswa(siswa: Siswa)
}

// 2. Class OfflineRepositoriSiswa
// Ini adalah "pelaksana" dari kontrak di atas khusus untuk mode Offline (Database Lokal).
class OfflineRepositoriSiswa(
    private val siswaDao: SiswaDao // Membutuhkan DAO untuk bekerja
): RepositoriSiswa {

    // Fungsi-fungsi di bawah ini sebenarnya hanya meneruskan perintah
    // dari Repository ke DAO.

    override fun getAllSiswaStream(): Flow<List<Siswa>> = siswaDao.getAllSiswa()

    override suspend fun insertSiswa(siswa: Siswa) = siswaDao.insert(siswa)

    override fun getSiswaStream(id: Int): Flow<Siswa?> = siswaDao.getSiswa(id)

    override suspend fun deleteSiswa(siswa: Siswa) = siswaDao.delete(siswa)

    override suspend fun updateSiswa(siswa: Siswa) = siswaDao.update(siswa)
}