package com.assessment.mobileengineerassesment.view.gallery

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentGalleryParentBinding

class GalleryParentFragment : BaseFragment<FragmentGalleryParentBinding>() {
    private val viewModel: GalleryFragmentVM by activityViewModels()

    override fun layoutId(): Int = R.layout.fragment_gallery_parent

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        val pageAdapter = SlidingPageAdapter(this)

        bindingView.galleryPager.apply {
            adapter = pageAdapter
        }

        bindingView.dotsIndicator.setViewPager2(bindingView.galleryPager)

        observeNavigationDestination()
    }

    private fun observeNavigationDestination() {
        viewModel.navigateToDestination.observe(viewLifecycleOwner) {
            if (!it.hasBeenHandled) {
                it.getContentIfNotHandled().let {
                    findNavController().navigate(R.id.action_galleryFragment_to_imageDetailsFragment)
                }
            }
        }
    }
}
