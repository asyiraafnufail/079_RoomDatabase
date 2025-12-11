package com.example.roomdatabase.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// 1. Anotasi @Dao
// Menandakan bahwa interface ini bertanggung jawab untuk mengakses database.
// Room akan otomatis membuatkan kode implementasinya di belakang layar.
@Dao
interface SiswaDao {

    // 2. Query (Membaca Data)
    // Mengambil semua data dari tabel 'tblSiswa' dan mengurutkannya sesuai abjad nama (ASC).
    // Mengembalikan 'Flow' artinya memberikan update data secara real-time ke UI.
    @Query("SELECT * FROM tblSiswa ORDER BY nama ASC")
    fun getAllSiswa(): Flow<List<Siswa>>

    // 3. Insert (Menambah Data)
    // onConflict = IGNORE: Jika ada data dengan ID yang sama persis,
    // Room akan mengabaikan perintah ini (tidak crash, tapi data tidak diduplikasi).
    // Menggunakan 'suspend' agar proses penyimpanan tidak memblokir UI (anti-lag).
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(siswa: Siswa)

    // 4. Query dengan Parameter
    // Mengambil satu data spesifik.
    // Tanda titik dua (:id) artinya mengambil nilai dari parameter fungsi 'id' (Int)
    // dan memasukkannya ke dalam query SQL.
    @Query("SELECT * from tblSiswa WHERE id = :id")
    fun getSiswa(id: Int): Flow<Siswa?>

    // 5. Delete (Menghapus Data)
    // Room akan mencari baris data yang punya ID sama dengan objek 'siswa' yang dikirim,
    // lalu menghapusnya.
    @Delete
    suspend fun delete(siswa: Siswa)

    // 6. Update (Memperbarui Data)
    // Room akan mencari baris data yang punya ID sama, lalu mengganti isi
    // nama, alamat, dan telpon dengan data baru yang dikirim.
    @Update
    suspend fun update(siswa: Siswa)
}