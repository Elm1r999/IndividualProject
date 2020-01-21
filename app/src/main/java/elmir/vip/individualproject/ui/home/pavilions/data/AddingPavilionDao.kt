package elmir.vip.individualproject.ui.home.pavilions.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * The Data Access Object for the [AddingPavilion] class.
 */
@Dao
interface AddingPavilionDao {
    @Query("SELECT * FROM adding_pavilions")
    fun getAddedPavilions(): LiveData<List<AddingPavilion>>

    @Query("SELECT EXISTS(SELECT 1 FROM adding_pavilions WHERE pavilion_id = :pavilionId LIMIT 1)")
    fun isAdded(pavilionId: String): LiveData<Boolean>

    /**
     * This query will tell Room to query both the [Pavilion] and [AddingPavilion] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM pavilions WHERE id IN (SELECT DISTINCT(pavilion_id) FROM adding_pavilions)")
    fun getPavilions(): LiveData<List<PavilionSelections>>

    @Insert
    suspend fun insertAddedPavilion(addingPavilion: AddingPavilion): Long

    @Delete
    suspend fun deleteAddedPavilion(addingPavilion: AddingPavilion)
}