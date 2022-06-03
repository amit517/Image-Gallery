package com.assessment.mobileengineerassesment.view.gallery

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentGalleryBinding
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.view.gallery.adapters.ImagePageAdapter
import com.assessment.mobileengineerassesment.view.gallery.adapters.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    private val viewModel: SharedGalleryVM by activityViewModels()
    private val imagePageAdapter by lazy { ImagePageAdapter(imageClickCallback) }

    private val imageClickCallback = { imageResponse: ImageResponse? ->
        viewModel.navigateToImageDetails(imageResponse)
    }

    override fun layoutId(): Int = R.layout.fragment_gallery

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        initUi()

        observeImageList()

       // observePagerState()
    }

    override fun onResume() {
        super.onResume()
        requireArguments().getInt(ARG_ORDER_BY).let { resId ->
            viewModel.submitOrderList(getString(resId))
            bindingView.headerTextView.text = getString(resId)
        }
    }

    private fun initUi() {
        bindingView.imageRecyclerViewView.adapter = imagePageAdapter.withLoadStateFooter(
            footer = PagingLoadStateAdapter { imagePageAdapter.retry() }
        )
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
            imagePageAdapter.loadStateFlow.collect {
                //todo
            }
        }
    }

    companion object {
        const val ARG_ORDER_BY = "ARG_ORDER_BY"
    }
}
