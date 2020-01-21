package elmir.vip.individualproject.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import elmir.vip.individualproject.ui.home.pavilions.data.AppDatabase
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionRepository
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.PavilionDetailViewModel
import elmir.vip.individualproject.utilities.getValue
import elmir.vip.individualproject.utilities.testPavilion
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PavilionDetailViewModelTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: PavilionDetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        val plantRepo = PavilionRepository.getInstance(appDatabase.pavilionDao())
        val gardenPlantingRepo = AddingPavilionRepository.getInstance(
                appDatabase.addingPavilionDao())
        viewModel = PavilionDetailViewModel(plantRepo, gardenPlantingRepo, testPavilion.pavilionId)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        assertFalse(getValue(viewModel.isAdded))
    }
}