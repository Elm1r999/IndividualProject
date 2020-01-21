package elmir.vip.individualproject.ui.home.pavilions.data

class AddingPavilionRepository private constructor(
    private val addingPavilionDao: AddingPavilionDao
) {

    suspend fun createPavilionMenu(pavilionId: String) {
        val addingPavilion = AddingPavilion(pavilionId)
        addingPavilionDao.insertAddedPavilion(addingPavilion)
    }

    suspend fun removePavilion(addingPavilion: AddingPavilion) {
        addingPavilionDao.deleteAddedPavilion(addingPavilion)
    }

    fun isAdded(plantId: String) = addingPavilionDao.isAdded(plantId)

    fun getAddedPavilionsMenu() = addingPavilionDao.getPavilions()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AddingPavilionRepository? = null

        fun getInstance(addingPavilionDao: AddingPavilionDao) =
                instance ?: synchronized(this) {
                    instance ?: AddingPavilionRepository(addingPavilionDao).also { instance = it }
                }
    }
}