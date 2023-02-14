package com.tngdev.movieappqst.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tngdev.movieappqst.databinding.FragmentMovieDetailBinding
import com.tngdev.movieappqst.ui.list.MovieListViewModel
import com.tngdev.movieappqst.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: MovieDetailFragmentArgs by navArgs()

    val movieItem get() = args.movieItem

    private val viewModel by lazy { ViewModelProvider(this).get(MovieDetailViewModel::class.java) }

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
    }

    private fun bindEvent() {
        binding.btBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btAddToWatchlist.setOnClickListener {
            viewModel.updateOnMyWatchList(movieItem, !args.movieItem.isOnMyWatchList)

            binding.movieItem = movieItem
            binding.executePendingBindings()
        }

        binding.btWatchTrailer.setOnClickListener {
            openTrailerLink(movieItem.trailerLink)
        }
    }

    private fun openTrailerLink(trailerLink: String) {
        if (trailerLink.contains("youtube")) {
            Utils.openYoutubeLink(
                requireContext(),
                trailerLink.substring(trailerLink.indexOf('=') + 1)
            )
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