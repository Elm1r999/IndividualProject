package elmir.vip.individualproject

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import elmir.vip.individualproject.ui.home.pavilions.PavilionActivity
import org.junit.Rule
import org.junit.Test

class PavilionActivityTest {

    @Rule @JvmField
    var activityTestRule = ActivityTestRule(PavilionActivity::class.java)

    @Test fun clickAddPavilion_OpensPavList() {
        // Given that no Pavilions are added to the user's menu

        // When the "Add Pavilion" button is clicked
        onView(withId(R.id.add_pavilion)).perform(click())

        // Then the ViewPager should change to the Pavilion List page
        onView(withId(R.id.pav_list)).check(matches(isDisplayed()))
    }
}
