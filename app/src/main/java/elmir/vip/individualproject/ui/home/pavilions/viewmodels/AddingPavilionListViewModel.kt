package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionSelections

class AddingPavilionListViewModel internal constructor(
    addingPavilionRepository: AddingPavilionRepository
) : ViewModel() {
    val pavilionSelections: LiveData<List<PavilionSelections>> =
            addingPavilionRepository.getAddedPavilionsMenu()
}