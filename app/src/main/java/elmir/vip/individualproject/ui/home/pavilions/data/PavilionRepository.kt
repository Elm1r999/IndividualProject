package elmir.vip.individualproject.ui.home.pavilions.data

/**
 * Repository module for handling data operations.
 */
class PavilionRepository private constructor(private val pavilionDao: PavilionDao) {

    fun getPavilions() = pavilionDao.getPavilion()

    fun getPavilions(plantId: String) = pavilionDao.getEachPavilion(plantId)

    fun getPavsWithZoneNumber(zoneNumber: Int) =
            pavilionDao.getPavilionWithZoneNumber(zoneNumber)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: PavilionRepository? = null

        fun getInstance(pavilionDao: PavilionDao) =
                instance ?: synchronized(this) {
                    instance ?: PavilionRepository(pavilionDao).also { instance = it }
                }
    }
}
