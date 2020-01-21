package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionRepository

/**
 * Factory for creating a [AddingPavilionListViewModel] with a constructor that takes a
 * [AddingPavilionRepository].
 */
class AddingPavilionListViewModelFactory(
    private val repository: AddingPavilionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddingPavilionListViewModel(repository) as T
    }
}