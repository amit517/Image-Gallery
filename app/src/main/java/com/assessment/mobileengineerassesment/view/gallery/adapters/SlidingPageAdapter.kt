package com.assessment.mobileengineerassesment.view.gallery.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.view.gallery.GalleryFragment
import com.assessment.mobileengineerassesment.view.gallery.GalleryFragment.Companion.ARG_ORDER_BY

class SlidingPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()

        return when (position) {
            0 -> {
                val galleryFragment = GalleryFragment()
                bundle.putInt(ARG_ORDER_BY, R.string.latest)
                galleryFragment.arguments = bundle
                galleryFragment
            }

            1 -> {
                val galleryFragment = GalleryFragment()
                bundle.putInt(ARG_ORDER_BY, R.string.popular)
                galleryFragment.arguments = bundle
                galleryFragment
            }

            else -> {
                val galleryFragment = GalleryFragment()
                bundle.putInt(ARG_ORDER_BY, R.string.oldest)
                galleryFragment.arguments = bundle
                galleryFragment
            }
        }
    }
}
