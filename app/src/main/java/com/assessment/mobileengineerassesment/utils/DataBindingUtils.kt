package com.assessment.mobileengineerassesment.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.assessment.mobileengineerassesment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object DataBindingUtils {
    @JvmStatic
    @BindingAdapter("loadImageWithGlide")
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        imageView.loadImage(imageUrl)
    }

    @JvmStatic
    @BindingAdapter("setVisible")
    fun setVisibilityBasedOnBoolean(view: View, value: Boolean) {
        if (value) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE
    }
}

fun ImageView.loadImage(url: String?) {
    try {
        url?.let {
            Glide.with(this.context)
                .load(Uri.parse(url))
                .transform(CenterInside(), RoundedCorners(24))
                .timeout(60 * 1000)
                .placeholder(R.drawable.image_place_holder)
                .error(R.drawable.image_place_holder)
                .into(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun ImageView.loadImage(url: String?, placeholder: Bitmap) {
    try {
        url?.let {
            Glide.with(this.context)
                .load(Uri.parse(url))
                .timeout(60 * 1000)
                .placeholder(BitmapDrawable(resources, placeholder))
                .error(R.drawable.image_place_holder)
                .centerCrop()
                .into(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
