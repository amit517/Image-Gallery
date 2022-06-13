package com.assessment.base.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.assessment.base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object DataBindingUtils {
    @JvmStatic
    @BindingAdapter(value = ["loadImageWithGlide", "placeHolderColor"])
    fun loadImage(imageView: ImageView, imageUrl: String?, color: String?) {
        imageView.loadImage(imageUrl, color)
    }

    @JvmStatic
    @BindingAdapter("setVisible")
    fun setVisibilityBasedOnBoolean(view: View, value: Boolean) {
        if (value) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE
    }
}

fun ImageView.loadImage(url: String?, color: String?) {
    try {
        url?.let {
            Glide.with(this.context)
                .load(Uri.parse(url))
                .transform(CenterInside(), RoundedCorners(8))
                .timeout(60 * 1000)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(ColorDrawable(Color.parseColor(color)))
                .error(R.drawable.image_place_holder)
                .into(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
