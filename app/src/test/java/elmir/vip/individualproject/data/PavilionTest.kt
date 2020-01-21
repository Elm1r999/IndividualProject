package elmir.vip.individualproject.data

import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

class PavilionTest {

    private lateinit var pavilion: Pavilion

    @Before fun setUp() {
        pavilion = Pavilion("1", "Tomato", "A red vegetable", 1, 2, "")
    }

    @Test fun test_default_values() {
        val defaultPlant = Pavilion("2", "Apple", "Description", 1)
        assertEquals(7, defaultPlant.visitingInterval)
        assertEquals("", defaultPlant.imageUrl)
    }

    @Test fun test_shouldBeVisited() {
        Calendar.getInstance().let { now ->
            // Generate lastWateringDate from being as copy of now.
            val lastWateringDate = Calendar.getInstance()

            // Test for lastWateringDate is today.
            lastWateringDate.time = now.time
            assertFalse(pavilion.visitedDate(now, lastWateringDate.apply { add(DAY_OF_YEAR, -0) }))

            // Test for lastWateringDate is yesterday.
            lastWateringDate.time = now.time
            assertFalse(pavilion.visitedDate(now, lastWateringDate.apply { add(DAY_OF_YEAR, -1) }))

            // Test for lastWateringDate is the day before yesterday.
            lastWateringDate.time = now.time
            assertFalse(pavilion.visitedDate(now, lastWateringDate.apply { add(DAY_OF_YEAR, -2) }))

            // Test for lastWateringDate is some days ago, three days ago, four days ago etc.
            lastWateringDate.time = now.time
            assertTrue(pavilion.visitedDate(now, lastWateringDate.apply { add(DAY_OF_YEAR, -3) }))
        }
    }

    @Test fun test_toString() {
        assertEquals("Tomato", pavilion.toString())
    }
}