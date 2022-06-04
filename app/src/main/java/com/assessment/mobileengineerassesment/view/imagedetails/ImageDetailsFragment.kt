package com.assessment.mobileengineerassesment.view.imagedetails

import androidx.navigation.fragment.navArgs
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentImageDetailsBinding
import com.assessment.mobileengineerassesment.utils.loadImage

class ImageDetailsFragment : BaseFragment<FragmentImageDetailsBinding>() {
    private val args: ImageDetailsFragmentArgs by navArgs()

    override fun layoutId(): Int = R.layout.fragment_image_details

    override fun getViewModel(): BaseViewModel? = null

    override fun initOnCreateView() {
        bindingView.visibleImageView.loadImage(args.imageUrl, args.bitmapImage)
    }
}
