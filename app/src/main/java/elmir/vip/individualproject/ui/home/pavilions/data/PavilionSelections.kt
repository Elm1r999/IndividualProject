package elmir.vip.individualproject.ui.home.pavilions.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [Pavilion] and a user's [AddingPavilion], which is
 * used by Room to fetch the related entities.
 */
data class PavilionSelections(
    @Embedded
    val pavilion: Pavilion,

    @Relation(parentColumn = "id", entityColumn = "pavilion_id")
    val addingPavilions: List<AddingPavilion> = emptyList()
)
