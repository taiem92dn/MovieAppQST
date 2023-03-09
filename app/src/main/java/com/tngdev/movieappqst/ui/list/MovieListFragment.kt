package com.tngdev.movieappqst.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tngdev.movieappqst.data.SortBy
import com.tngdev.movieappqst.databinding.FragmentMovieListBinding
import com.tngdev.movieappqst.model.MovieItem
import com.tngdev.movieappqst.ui.bottomdialog.SortBottomSheetDialog
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

        setFragmentResultListener(SortBottomSheetDialog.REQUEST_KEY_SORT_BY) { requestKey, bundle ->
            val sortByStr = bundle.getString(
                SortBottomSheetDialog.BUNDLE_KEY_SORT_BY,
                SortBy.None.toString()
            )
            viewModel.setSortBy(SortBy.valueOf(sortByStr))

            Toast.makeText(requireContext(), "Sorted by $sortByStr", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun bindData() {
    }

    private fun bindEvent() {
        moviesAdapter.onItemClickListener = {
            navigateToMovieDetail(it)
        }

        binding.tvSort.setOnClickListener {
            SortBottomSheetDialog.newInstance(viewModel.sortByState.value)
                .show(parentFragmentManager, SortBottomSheetDialog.TAG)
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