package com.tngdev.movieappqst.ui.bottomdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tngdev.movieappqst.R
import com.tngdev.movieappqst.data.SortBy
import com.tngdev.movieappqst.databinding.FragmentSortBottomSheetBinding


class SortBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {
        val TAG = SortBottomSheetDialog::class.simpleName
        const val REQUEST_KEY_SORT_BY = "REQUEST_KEY_SORT_BY"
        const val BUNDLE_KEY_SORT_BY = "BUNDLE_KEY_SORT_BY"

        fun newInstance(sortBy: SortBy?) = SortBottomSheetDialog().apply {
            arguments = bundleOf("sortBy" to sortBy.toString())
        }
    }

    private val sortBy get() = SortBy.valueOf(
        arguments?.getString("sortBy", SortBy.None.toString()) ?: SortBy.None.toString()
    )

    private lateinit var binding: FragmentSortBottomSheetBinding

    override fun getTheme() = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setOnClickListener()
    }

    private fun setupUI() {
        binding.apply {
            if (sortBy != SortBy.ReleasedDate) {
                tvOptReleaseDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            }
            if (sortBy != SortBy.Title) {
                tvOptTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            }
        }
    }

    private fun setOnClickListener() {
        binding.apply {
            tvCancel.setOnClickListener {
                dismiss()
            }

            tvOptTitle.setOnClickListener {
                setResult(SortBy.Title)
                dismiss()
            }

            tvOptReleaseDate.setOnClickListener {
                setResult(SortBy.ReleasedDate)
                dismiss()
            }
        }
    }

    private fun setResult(sortBy: SortBy) {
        setFragmentResult(
            REQUEST_KEY_SORT_BY,
            bundleOf(BUNDLE_KEY_SORT_BY to sortBy.toString())
        )
    }
}
