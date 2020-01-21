package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionRepository

/**
 * Factory for creating a [PavilionListViewModel] with a constructor that takes a [PavilionRepository].
 */
class PavilionListViewModelFactory (private val repository: PavilionRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = PavilionListViewModel(repository) as T
}
