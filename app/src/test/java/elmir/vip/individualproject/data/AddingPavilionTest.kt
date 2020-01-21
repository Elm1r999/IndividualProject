package elmir.vip.individualproject.data

import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilion
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR

class AddingPavilionTest {

    @Test
    fun testDefaultValues() {
        val gardenPlanting = AddingPavilion("1")
        val cal = Calendar.getInstance()
        assertYMD(cal, gardenPlanting.visitDate)
        assertYMD(cal, gardenPlanting.lastVisitDate)
        assertEquals(0L, gardenPlanting.pAddedId)
    }

    // Only Year/Month/Day precision is needed for comparing AddingPavilion Calendar entries
    private fun assertYMD(expectedCal: Calendar, actualCal: Calendar) {
        assertThat(actualCal.get(YEAR), equalTo(expectedCal.get(YEAR)))
        assertThat(actualCal.get(MONTH), equalTo(expectedCal.get(MONTH)))
        assertThat(actualCal.get(DAY_OF_MONTH), equalTo(expectedCal.get(DAY_OF_MONTH)))
    }
}