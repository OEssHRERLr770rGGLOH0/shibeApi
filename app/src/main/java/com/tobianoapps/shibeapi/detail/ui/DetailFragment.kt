package com.tobianoapps.shibeapi.detail.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.tobianoapps.shibeapi.R
import com.tobianoapps.shibeapi.databinding.FragmentDetailBinding
import com.tobianoapps.shibeapi.list.api.ShibeModels.Shibe
import com.tobianoapps.shibeapi.list.ui.ListFragment

/**
 * This class shows detail of a [Shibe] clicked in the [ListFragment]'s recycler view.
 */
class DetailFragment : Fragment() {

    /*** PROPERTIES ***/
    private val args : DetailFragmentArgs by navArgs()

    // View binding
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate element transition
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    /*** LIFECYCLE ***/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate binding
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        // Set binding properties
        binding.lifecycleOwner = viewLifecycleOwner
        binding.shibe = args.shibe

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}