package elmir.vip.individualproject.ui.home.pavilions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import elmir.vip.individualproject.R
import elmir.vip.individualproject.databinding.FragmentPavilionDetailBinding
import elmir.vip.individualproject.ui.home.pavilions.data.Pavilion
import elmir.vip.individualproject.ui.home.pavilions.utilities.InjectorUtils
import elmir.vip.individualproject.ui.home.pavilions.viewmodels.PavilionDetailViewModel

/**
 * A fragment representing a single Pavilion detail screen.
 */

class PavilionDetailFragment : Fragment() {

    private val args: PavilionDetailFragmentArgs by navArgs()

    private val pavilionDetailViewModel: PavilionDetailViewModel by viewModels {
        InjectorUtils.providePavilionDetailViewModelFactory(requireActivity(), args.pavId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPavilionDetailBinding>(
            inflater, R.layout.fragment_pavilion_detail, container, false
        ).apply {
            viewModel = pavilionDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = object : Callback {
                override fun add(pavilion: Pavilion?) {
                    pavilion?.let {
                        hideAppBarFab(fab)
                        pavilionDetailViewModel.addPavilion()
                        Snackbar.make(root, R.string.added_pavilion_to_list, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            pavilionDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the pavilion name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent()
                        true
                    }
                    else -> false
                }
            }
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = pavilionDetailViewModel.pavilion.value.let { plant ->
            if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_pavilion, plant.name)
            }
        }
        val shareIntent = activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setText(shareText)
                .setType("text/plain")
                .createChooserIntent()
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        }
        startActivity(shareIntent)
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // Turn this behavior off to hide the FAB when it is clicked.
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    interface Callback {
        fun add(plant: Pavilion?)
    }
}
