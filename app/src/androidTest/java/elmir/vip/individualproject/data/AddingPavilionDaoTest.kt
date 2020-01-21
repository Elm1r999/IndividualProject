package elmir.vip.individualproject.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import elmir.vip.individualproject.ui.home.pavilions.data.AppDatabase
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilion
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilionDao
import elmir.vip.individualproject.utilities.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddingPavilionDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var addingPavilionDao: AddingPavilionDao
    private var testAddedPavilionId: Long = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        addingPavilionDao = database.addingPavilionDao()

        database.pavilionDao().insertAll(testPavilions)
        testAddedPavilionId = addingPavilionDao.insertAddedPavilion(testAddingPavilions)
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetAddedPavilions() = runBlocking {
        val addingPavil = AddingPavilion(
            testPavilions[1].pavilionId,
            testCalendar,
            testCalendar
        ).also { it.pAddedId = 2 }
        addingPavilionDao.insertAddedPavilion(addingPavil)
        assertThat(getValue(addingPavilionDao.getAddedPavilions()).size, equalTo(2))
    }

    @Test fun testDeleteAddedPavilions() = runBlocking {
        val gardenPlanting2 = AddingPavilion(
                testPavilions[1].pavilionId,
                testCalendar,
                testCalendar
        ).also { it.pAddedId = 2 }
        addingPavilionDao.insertAddedPavilion(gardenPlanting2)
        assertThat(getValue(addingPavilionDao.getAddedPavilions()).size, equalTo(2))
        addingPavilionDao.deleteAddedPavilion(gardenPlanting2)
        assertThat(getValue(addingPavilionDao.getAddedPavilions()).size, equalTo(1))
    }

    @Test fun testIfPavilionAdded() {
        assertTrue(getValue(addingPavilionDao.isAdded(testPavilion.pavilionId)))
    }

    @Test fun testIfPavilionAdded_notFound() {
        assertFalse(getValue(addingPavilionDao.isAdded(testPavilions[2].pavilionId)))
    }

    @Test fun testPavilionsMenu() {
        val menuSections = getValue(addingPavilionDao.getPavilions())
        assertThat(menuSections.size, equalTo(1))

        assertThat(menuSections[0].pavilion, equalTo(testPavilion))
        assertThat(menuSections[0].addingPavilions.size, equalTo(1))
        assertThat(menuSections[0].addingPavilions[0], equalTo(testAddingPavilions))
    }
}