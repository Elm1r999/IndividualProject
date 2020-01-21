package elmir.vip.individualproject.ui.home.pavilions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import elmir.vip.individualproject.R
import elmir.vip.individualproject.databinding.FragmentViewPagerBinding
import elmir.vip.individualproject.ui.home.pavilions.adapters.MY_FAVOURITE_PAVILION_PAGE_INDEX
import elmir.vip.individualproject.ui.home.pavilions.adapters.PAVILION_LIST_PAGE_INDEX
import elmir.vip.individualproject.ui.home.pavilions.adapters.SunflowerPagerAdapter

class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SunflowerPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_FAVOURITE_PAVILION_PAGE_INDEX -> R.drawable.pavilion_tab_selector
            PAVILION_LIST_PAGE_INDEX -> R.drawable.pavilion_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_FAVOURITE_PAVILION_PAGE_INDEX -> getString(R.string.my_list_title)
            PAVILION_LIST_PAGE_INDEX -> getString(R.string.pavilion_list_title)
            else -> null
        }
    }
}