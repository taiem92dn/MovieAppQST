package com.tngdev.movieappqst.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tngdev.movieappqst.R
import com.tngdev.movieappqst.databinding.FragmentMovieListBinding
import com.tngdev.movieappqst.model.MovieItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by lazy { ViewModelProvider(this).get(MovieListViewModel::class.java) }

    private lateinit var moviesAdapter : MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeData()
        bindEvent()
        bindData()

        binding.rvMovieList.adapter = moviesAdapter
    }

    private fun bindData() {
    }

    private fun bindEvent() {
        moviesAdapter.onItemClickListener = {
            navigateToMovieDetail(it)
        }
    }

    private fun initAdapter() {
        moviesAdapter = MovieListAdapter()
        binding.rvMovieList.apply {
            setHasFixedSize(true)
        }

    }

    private fun navigateToMovieDetail(movieItem: MovieItem) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
            movieItem
        )

        findNavController().navigate(action)
    }

    private fun observeData() {
        viewModel.movieList.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}