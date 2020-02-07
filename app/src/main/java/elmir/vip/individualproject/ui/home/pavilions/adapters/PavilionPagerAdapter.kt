package elmir.vip.individualproject.ui.home.pavilions.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import elmir.vip.individualproject.ui.home.pavilions.PavilionMenuFragment
import elmir.vip.individualproject.ui.home.pavilions.PavilionListFragment

const val MY_FAVOURITE_PAVILION_PAGE_INDEX = 0
const val PAVILION_LIST_PAGE_INDEX = 1

class PavilionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_FAVOURITE_PAVILION_PAGE_INDEX to { PavilionMenuFragment() },
        PAVILION_LIST_PAGE_INDEX to { PavilionListFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}