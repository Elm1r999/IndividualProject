package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import elmir.vip.individualproject.ui.home.pavilions.data.PavilionSelections
import java.text.SimpleDateFormat
import java.util.Locale

class PavilionSelectionsViewModel(selections: PavilionSelections) {
    private val pavilion = checkNotNull(selections.pavilion)
    private val addingPavilion = selections.addingPavilions[0]

    val visitDateString: String = dateFormat.format(addingPavilion.lastVisitDate.time)
    val visitInterval
        get() = pavilion.visitingInterval
    val imageUrl
        get() = pavilion.imageUrl
    val pavilionName
        get() = pavilion.name
    val pavilionDateString: String = dateFormat.format(addingPavilion.visitDate.time)
    val pavilionId
        get() = pavilion.pavilionId

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}