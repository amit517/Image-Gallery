package com.assessment.mobileengineerassesment.view.gallery

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
    private val viewModel: GalleryFragmentVM by viewModels()
    private val imagePageAdapter by lazy { ImagePageAdapter(imageClickCallback) }

    private val imageClickCallback = { imageresponse: ImageResponse? ->
        findNavController().navigate(R.id.action_galleryFragment_to_imageDetailsFragment)
    }

    override fun layoutId(): Int = R.layout.fragment_gallery

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        initUi()

        observeImageList()

        observePagerState()
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
}
