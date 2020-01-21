package elmir.vip.individualproject.ui.home.pavilions.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import elmir.vip.individualproject.ui.home.pavilions.PavilionListFragment
import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionRepository

/**
 * The ViewModel for [PavilionListFragment].
 */
class PavilionListViewModel internal constructor(pavilionRepository: PavilionRepository) : ViewModel() {

    private val growZoneNumber = MutableLiveData<Int>(NO_GROW_ZONE)

    val pavilions: LiveData<List<Pavilion>> = growZoneNumber.switchMap {
        if (it == NO_GROW_ZONE) {
            pavilionRepository.getPavilions()
        } else {
            pavilionRepository.getPavsWithZoneNumber(it)
        }
    }

    fun setGrowZoneNumber(num: Int) {
        growZoneNumber.value = num
    }

    fun clearGrowZoneNumber() {
        growZoneNumber.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZoneNumber.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
    }
}
