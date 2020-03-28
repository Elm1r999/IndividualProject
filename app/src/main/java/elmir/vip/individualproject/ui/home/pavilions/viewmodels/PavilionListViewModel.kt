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

    private val zoneNumber = MutableLiveData<Int>(NO_ITEM_ZONE)

    val pavilions: LiveData<List<Pavilion>> = zoneNumber.switchMap {
        if (it == NO_ITEM_ZONE) {
            pavilionRepository.getPavilions()
        } else {
            pavilionRepository.getPavsWithZoneNumber(it)
        }
    }

    fun setZoneNumber(num: Int) {
        zoneNumber.value = num
    }

    fun clearZoneNumber() {
        zoneNumber.value = NO_ITEM_ZONE
    }

    fun isFiltered() = zoneNumber.value != NO_ITEM_ZONE

    companion object {
        private const val NO_ITEM_ZONE = -1
    }
}
