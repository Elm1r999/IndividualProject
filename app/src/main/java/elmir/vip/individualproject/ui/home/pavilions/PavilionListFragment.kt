package elmir.vip.individualproject.ui.home.pavilions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import elmir.vip.individualproject.R
import elmir.vip.individualproject.databinding.FragmentPavilionListBinding
import elmir.vip.individualproject.ui.home.pavilions.adapters.PavilionAdapter
import elmir.vip.individualproject.ui.home.pavilions.utilities.InjectorUtils
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.PavilionListViewModel

class PavilionListFragment : Fragment() {

    private val viewModel: PavilionListViewModel by viewModels {
        InjectorUtils.providePavilionListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPavilionListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = PavilionAdapter()
        binding.pavList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_pavilion_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filterItems -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: PavilionAdapter) {
        viewModel.pavilions.observe(viewLifecycleOwner) { pavilions ->
            adapter.submitList(pavilions)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearZoneNumber()
            } else {
                setZoneNumber(9)
            }
        }
    }
}