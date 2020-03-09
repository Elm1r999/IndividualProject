package elmir.vip.individualproject.ui.home.pavilions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import elmir.vip.individualproject.databinding.ListItemPavilionBinding
import elmir.vip.individualproject.ui.home.pavilions.HomeViewPagerFragmentDirections
import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
import elmir.vip.individualproject.ui.home.pavilions.PavilionListFragment

/**
 * Adapter for the [RecyclerView] in [PavilionListFragment].
 */
class PavilionAdapter : ListAdapter<Pavilion, RecyclerView.ViewHolder>(PavilionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PavilionViewHolder(
            ListItemPavilionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PavilionViewHolder).bind(plant)
    }

    class PavilionViewHolder(
        private val binding: ListItemPavilionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.pavilion?.let { plant ->
                    navigateToPavilion(plant, it)
                }
            }
        }

        private fun navigateToPavilion(
            plant: Pavilion,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPavilionDetailFragment(
                    plant.pavilionId
                )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Pavilion) {
            binding.apply {
                pavilion = item
                executePendingBindings()
            }
        }
    }
}

private class PavilionDiffCallback : DiffUtil.ItemCallback<Pavilion>() {

    override fun areItemsTheSame(oldItem: Pavilion, newItem: Pavilion): Boolean {
        return oldItem.pavilionId == newItem.pavilionId
    }

    override fun areContentsTheSame(oldItem: Pavilion, newItem: Pavilion): Boolean {
        return oldItem == newItem
    }
}