package elmir.vip.individualproject.ui.home.pavilions.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * [AddingPavilion] represents when a user adds a [Pavilion] to their menu, with useful metadata.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "adding_pavilions",
    foreignKeys = [
        ForeignKey(entity = Pavilion::class, parentColumns = ["id"], childColumns = ["pavilion_id"])
    ],
    indices = [Index("pavilion_id")]
)
data class AddingPavilion(
    @ColumnInfo(name = "pavilion_id") val pavilionId: String,

    /**
     * Indicates when the [Pavilion] was added. Used for showing notification when it's time
     * to visit the pavilion.
     */
    @ColumnInfo(name = "visit_date") val visitDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [Pavilion] was last visited. Used for showing notification when it's
     * time visit the pavilion.
     */
    @ColumnInfo(name = "visited_date")
    val lastVisitDate: Calendar = Calendar.getInstance()
) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var pAddedId: Long = 0
}