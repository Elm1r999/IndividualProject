package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
/**
 * Factory for creating a [PavilionDetailViewModel] with a constructor that takes a [PavilionRepository]
 * and an ID for the current [Pavilion].
 */
class PavilionDetailViewModelFactory(
    private val pavilionRepository: PavilionRepository,
    private val addingPavilionRepository: AddingPavilionRepository,
    private val pavilionID: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PavilionDetailViewModel(pavilionRepository, addingPavilionRepository, pavilionID) as T
    }
}
