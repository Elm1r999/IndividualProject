package elmir.vip.individualproject.ui.home.pavilions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import elmir.vip.individualproject.R
import elmir.vip.individualproject.databinding.FragmentPavilionsBinding
import elmir.vip.individualproject.ui.home.pavilions.adapters.PavilionAddingAdapter
import elmir.vip.individualproject.ui.home.pavilions.adapters.PAVILION_LIST_PAGE_INDEX
import elmir.vip.individualproject.ui.home.pavilions.utilities.InjectorUtils
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.AddingPavilionListViewModel

class PavilionMenuFragment : Fragment() {

    private lateinit var binding: FragmentPavilionsBinding

    private val viewModel: AddingPavilionListViewModel by viewModels {
        InjectorUtils.provideAddedPavilionListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPavilionsBinding.inflate(inflater, container, false)
        val adapter = PavilionAddingAdapter()
        binding.pMenuList.adapter = adapter

        binding.addPavilion.setOnClickListener {
            navigateToPavilionListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: PavilionAddingAdapter, binding: FragmentPavilionsBinding) {
        viewModel.pavilionSelections.observe(viewLifecycleOwner) { result ->
            binding.hasPavilions = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    // convert to data binding if applicable
    private fun navigateToPavilionListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PAVILION_LIST_PAGE_INDEX
    }
}
