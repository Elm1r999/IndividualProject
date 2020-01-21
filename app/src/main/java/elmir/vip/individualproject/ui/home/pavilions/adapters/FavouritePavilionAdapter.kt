package elmir.vip.individualproject.ui.home.pavilions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import elmir.vip.individualproject.R
import elmir.vip.individualproject.databinding.ListItemAddingPavilionsBinding
import elmir.vip.individualproject.ui.home.pavilions.HomeViewPagerFragmentDirections
import elmir.vip.individualproject.ui.home.pavilions.data.PavilionSelections
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.PavilionSelectionsViewModel

class PavilionAddingAdapter :
    ListAdapter<PavilionSelections, PavilionAddingAdapter.ViewHolder>(
        MenuSelectionDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_adding_pavilions, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemAddingPavilionsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.viewModel?.pavilionId?.let { pavilionId ->
                    navigateToPavilion(pavilionId, view)
                }
            }
        }

        private fun navigateToPavilion(plantId: String, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToPavilionDetailFragment(plantId)
            view.findNavController().navigate(direction)
        }

        fun bind(plantings: PavilionSelections) {
            with(binding) {
                viewModel = PavilionSelectionsViewModel(plantings)
                executePendingBindings()
            }
        }
    }
}

private class MenuSelectionDiffCallback : DiffUtil.ItemCallback<PavilionSelections>() {

    override fun areItemsTheSame(
        oldItem: PavilionSelections,
        newItem: PavilionSelections
    ): Boolean {
        return oldItem.pavilion.pavilionId == newItem.pavilion.pavilionId
    }

    override fun areContentsTheSame(
        oldItem: PavilionSelections,
        newItem: PavilionSelections
    ): Boolean {
        return oldItem.pavilion == newItem.pavilion
    }
}