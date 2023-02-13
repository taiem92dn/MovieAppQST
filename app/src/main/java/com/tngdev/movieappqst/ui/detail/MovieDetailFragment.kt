package com.tngdev.movieappqst.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tngdev.movieappqst.databinding.FragmentMovieDetailBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindData()
        bindEvent()
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_DetailFragment_to_MovieListFragment)
//        }
    }

    private fun bindEvent() {
        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindData() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            movieItem = args.movieItem
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}