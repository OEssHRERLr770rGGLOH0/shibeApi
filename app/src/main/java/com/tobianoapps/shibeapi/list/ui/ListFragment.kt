package com.tobianoapps.shibeapi.list.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tobianoapps.shibeapi.R
import com.tobianoapps.shibeapi.ShibeViewModel
import com.tobianoapps.shibeapi.databinding.FragmentListBinding
import com.tobianoapps.shibeapi.databinding.LoadingViewBinding
import com.tobianoapps.shibeapi.detail.ui.DetailFragment
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ListFragment : Fragment(), KoinComponent {

    /*** PROPERTIES ***/
    private val shibeViewModel: ShibeViewModel by activityViewModels()
    private lateinit var listAdapter: ListAdapter
    private val ioDispatcher: CoroutineDispatcher by inject()

    // View binding
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var _loadingViewBinding: LoadingViewBinding? = null
    private val loadingViewBinding get() = _loadingViewBinding

    /*** LIFECYCLE ***/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate binding
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )

        _loadingViewBinding = binding.loadingViewContainer

        // Set binding properties
        binding.lifecycleOwner = viewLifecycleOwner

        // Init [LiveData] observers
        subscribeToObservers()

        // set up shared element transition
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setupRecyclerView()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _loadingViewBinding = null
    }

    /**
     * This function sets up this fragment's [RecyclerView]'s [ListAdapter].
     *
     * When a Shibe card is clicked, we navigate to [DetailFragment] with the [NavController]
     * and invoke an element transition on the recycler item [ImageView] and [TextView]
     *
     */
    private fun setupRecyclerView() {

        // Init adapter and click listener
        listAdapter = ListAdapter(
            callback = { shibe, imageView, textView, layout ->
                findNavController()
                    .navigate(
                        directions = ListFragmentDirections.actionShibeListToShibeDetail(shibe),
                        navigatorExtras = FragmentNavigator.Extras.Builder()
                            .addSharedElements(
                                mapOf(
                                    imageView to imageView.transitionName,
                                    textView to textView.transitionName,
                                    layout to layout.transitionName
                                )
                            ).build()
                    )
            })



        // Setup recycler
        binding.recycler.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    /**
     * Observe Live Data emitted by [ShibeViewModel] and update the UI when it changes.
     */
    private fun subscribeToObservers() {

        // Update adapter
        shibeViewModel.shibeResponse.observe(viewLifecycleOwner) {
            it?.let {
                listAdapter.differ.submitList(it)
            }
        }

        // Toggle loading view
        shibeViewModel.showLoadingView.observe(viewLifecycleOwner) {
                toggleLoadingView(it)
        }
    }

    private fun toggleLoadingView(boolean: Boolean) {
        when (boolean) {
            true -> loadingViewBinding?.loadingRoot?.visibility = View.VISIBLE
            else -> loadingViewBinding?.loadingRoot?.visibility = View.GONE
        }
    }
}






