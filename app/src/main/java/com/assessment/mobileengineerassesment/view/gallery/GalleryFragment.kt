package com.assessment.mobileengineerassesment.view.gallery

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentGalleryBinding
import com.assessment.mobileengineerassesment.model.ImageResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    private val viewModel: GalleryFragmentVM by activityViewModels()
    private val imagePageAdapter by lazy { ImagePageAdapter(imageClickCallback) }

    private val imageClickCallback = { imageResponse: ImageResponse? ->
        viewModel.navigateToImageDetails(imageResponse)
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
            bindingView.headerTextView.text = getString(resId)
        }
    }

    private fun initUi() {
        bindingView.imageRecyclerViewView.adapter = imagePageAdapter
    }

    private fun observePagerState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            imagePageAdapter.loadStateFlow.collect {
                //todo
            }
        }
    }

    private fun observeImageList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.imageDataList.collectLatest {
                imagePageAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    companion object {
        const val ARG_ORDER_BY = "ARG_ORDER_BY"
    }
}
