package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import elmir.vip.individualproject.ui.home.pavilions.PavilionDetailFragment
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionRepository
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [PavilionDetailFragment].
 */
class PavilionDetailViewModel(
    pavilionRepository: PavilionRepository,
    private val addingPavilionRepository: AddingPavilionRepository,
    private val pavilionID: String
) : ViewModel() {

    val isAdded = addingPavilionRepository.isAdded(pavilionID)
    val pavilion = pavilionRepository.getPavilions(pavilionID)

    fun addPavilion() {
        viewModelScope.launch {
            addingPavilionRepository.createPavilionMenu(pavilionID)
        }
    }
}
