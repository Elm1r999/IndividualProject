package elmir.vip.individualproject.ui.home.pavilions.utilities

import android.content.Context
import elmir.vip.individualproject.ui.home.pavilions.data.AppDatabase
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.AddingPavilionListViewModelFactory
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.PavilionDetailViewModelFactory
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.PavilionListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getPavilionRepository(context: Context): PavilionRepository {
        return PavilionRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).pavilionDao())
    }

    private fun getAddingPavilionRepository(context: Context): AddingPavilionRepository {
        return AddingPavilionRepository.getInstance(
                AppDatabase.getInstance(context.applicationContext).addingPavilionDao())
    }

    fun provideAddedPavilionListViewModelFactory(context: Context): AddingPavilionListViewModelFactory {
        val repository = getAddingPavilionRepository(context)
        return AddingPavilionListViewModelFactory(repository)
    }

    fun providePavilionListViewModelFactory(context: Context): PavilionListViewModelFactory {
        val repository = getPavilionRepository(context)
        return PavilionListViewModelFactory(repository)
    }

    fun providePavilionDetailViewModelFactory(context: Context, pavilionId: String): PavilionDetailViewModelFactory {
        return PavilionDetailViewModelFactory(getPavilionRepository(context),
                getAddingPavilionRepository(context), pavilionId)
    }
}
