package elmir.vip.individualproject.utilities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import elmir.vip.individualproject.ui.home.pavilions.data.AddingPavilion
import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import java.util.Calendar

/**
 * [Pavilion] objects used for tests.
 */
val testPavilions = arrayListOf(
    Pavilion("1", "Apple", "A red fruit", 1),
    Pavilion("2", "B", "Description B", 1),
    Pavilion("3", "C", "Description C", 2)
)
val testPavilion = testPavilions[0]

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1998)
    set(Calendar.MONTH, Calendar.SEPTEMBER)
    set(Calendar.DAY_OF_MONTH, 4)
}

/**
 * [AddingPavilion] object used for tests.
 */
val testAddingPavilions = AddingPavilion(testPavilion.pavilionId, testCalendar, testCalendar)

/**
 * Returns the content description for the navigation button view in the toolbar.
 */
fun getToolbarNavigationContentDescription(activity: Activity, toolbarId: Int) =
    activity.findViewById<Toolbar>(toolbarId).navigationContentDescription as String

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
)