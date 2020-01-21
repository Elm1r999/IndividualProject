package elmir.vip.individualproject.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import elmir.vip.individualproject.ui.home.pavilions.data.AppDatabase
import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionDao
import elmir.vip.individualproject.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PavilionDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var pavilionDao: PavilionDao
    private val pavilionA = Pavilion("1", "A", "", 1, 1, "")
    private val pavilionB = Pavilion("2", "B", "", 1, 1, "")
    private val pavilionC = Pavilion("3", "C", "", 2, 2, "")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        pavilionDao = database.pavilionDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        pavilionDao.insertAll(listOf(pavilionB, pavilionC, pavilionA))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetPavilions() {
        val pavilionList = getValue(pavilionDao.getPavilion())
        assertThat(pavilionList.size, equalTo(3))

        // Ensure pavilion list is sorted by name
        assertThat(pavilionList[0], equalTo(pavilionA))
        assertThat(pavilionList[1], equalTo(pavilionB))
        assertThat(pavilionList[2], equalTo(pavilionC))
    }

    @Test fun testGetPavilionWithZoneNumber() {
        val pavilionList = getValue(pavilionDao.getPavilionWithZoneNumber(1))
        assertThat(pavilionList.size, equalTo(2))
        assertThat(getValue(pavilionDao.getPavilionWithZoneNumber(2)).size, equalTo(1))
        assertThat(getValue(pavilionDao.getPavilionWithZoneNumber(3)).size, equalTo(0))

        // Ensure pavilion list is sorted by name
        assertThat(pavilionList[0], equalTo(pavilionA))
        assertThat(pavilionList[1], equalTo(pavilionB))
    }

    @Test fun testGetPavilion() {
        assertThat(getValue(pavilionDao.getEachPavilion(pavilionA.pavilionId)), equalTo(pavilionA))
    }
}