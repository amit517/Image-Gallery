package com.assessment.mobileengineerassesment.view.gallery

import android.graphics.Bitmap
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentGalleryBinding
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.view.gallery.adapters.ImagePageAdapter
import com.assessment.mobileengineerassesment.view.gallery.adapters.PagingLoadStateAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    private val viewModel: SharedGalleryVM by activityViewModels()
    private val imagePageAdapter by lazy { ImagePageAdapter(imageClickCallback) }

    private val imageClickCallback = { imageResponse: ImageResponse?, bitmap: Bitmap ->
        viewModel.navigateToImageDetails(imageResponse, bitmap)
    }

    override fun layoutId(): Int = R.layout.fragment_gallery

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        initUi()

        observeImageList()

        observePagerState()
    }

    override fun onResume() {
        super.onResume()
        requireArguments().getInt(ARG_ORDER_BY).let { resId ->
            viewModel.submitOrderList(getString(resId))
            bindingView.headerTextView.text = getString(resId).replaceFirstChar { it.uppercase() }
        }
    }

    private fun initUi() {
        bindingView.apply {
            this.viewModel = viewModel
            imageRecyclerViewView.apply {
                adapter = imagePageAdapter.withLoadStateFooter(
                    footer = PagingLoadStateAdapter { imagePageAdapter.retry() }
                )
            }
        }

        bindingView.swipeRefresh.setOnRefreshListener {
            imagePageAdapter.refresh()
            bindingView.swipeRefresh.isRefreshing = false
        }
    }

    private fun observeImageList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.imageDataList.collectLatest {
                imagePageAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun observePagerState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            imagePageAdapter.loadStateFlow.collect { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        showLoadingState()
                    }
                    is LoadState.Error -> {
                        (loadState.refresh as LoadState.Error).error.message?.let {
                            showErrorState(it)
                        }
                    }
                    is LoadState.NotLoading -> {
                        showDefaultStatus()
                    }
                }
            }
        }
    }

    private fun showErrorState(errorMessage: String) {
        bindingView.apply {
            errorTextView.apply {
                text = errorMessage
                visibility = View.VISIBLE
            }
            loading.isLoading = false
            imageRecyclerViewView.visibility = View.GONE
        }

        Snackbar.make(bindingView.root, errorMessage, Snackbar.LENGTH_LONG)
            .setAction("Try Again") {
                imagePageAdapter.retry()
            }
            .setActionTextColor(ContextCompat.getColor(requireContext(),
                android.R.color.holo_red_light))
            .show()
    }

    private fun showLoadingState() {
        bindingView.apply {
            loading.isLoading = true
            errorTextView.visibility = View.GONE
            imageRecyclerViewView.visibility = View.GONE
        }
    }

    private fun showDefaultStatus() {
        bindingView.apply {
            loading.isLoading = false
            errorTextView.visibility = View.GONE
            imageRecyclerViewView.visibility = View.VISIBLE
        }
    }

    companion object {
        const val ARG_ORDER_BY = "ARG_ORDER_BY"
    }
}
