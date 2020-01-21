package elmir.vip.individualproject.ui.home.pavilions.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Pavilion class.
 */
@Dao
interface PavilionDao {
    @Query("SELECT * FROM pavilions ORDER BY name")
    fun getPavilion(): LiveData<List<Pavilion>>

    @Query("SELECT * FROM pavilions WHERE zoneNumber = :growZoneNumber ORDER BY name")
    fun getPavilionWithZoneNumber(growZoneNumber: Int): LiveData<List<Pavilion>>

    @Query("SELECT * FROM pavilions WHERE id = :pavilionId")
    fun getEachPavilion(pavilionId: String): LiveData<Pavilion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pavilions: List<Pavilion>)
}
