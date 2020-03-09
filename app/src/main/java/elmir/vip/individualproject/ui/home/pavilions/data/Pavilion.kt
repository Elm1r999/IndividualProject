package elmir.vip.individualproject.ui.home.pavilions.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

@Entity(tableName = "pavilions")
data class Pavilion(
    @PrimaryKey @ColumnInfo(name = "id") val pavilionId: String,
    val name: String,
    val description: String,
    val zoneNumber: Int,
    val visitingInterval: Int = 7, // when the pavilion was visited, in days
    val imageUrl: String = ""
) {

    /**
     * Determines if the pavilion should be visited. Returns true if [since]'s date > date of last
     * visit + visit Interval; false otherwise.
     */
    fun visitedDate(since: Calendar, lastVisitDate: Calendar) =
        since > lastVisitDate.apply {
            add(DAY_OF_YEAR, visitingInterval)
        }

    override fun toString() = name
}
