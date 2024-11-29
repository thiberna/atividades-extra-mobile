package up.ddm.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import up.ddm.Atributos

@Dao
interface AtributosDAO {
    @Insert
    suspend fun insert(atributos: Atributos)

    @Query("SELECT * FROM atributos WHERE id = :id")
    suspend fun getAtributosById(id: Int): Atributos

    @Query("DELETE FROM atributos WHERE id = :id")
    suspend fun deleteAtributosById(id: Int)
}
