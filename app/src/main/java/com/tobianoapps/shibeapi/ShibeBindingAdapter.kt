package com.tobianoapps.shibeapi

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import coil.memory.MemoryCache

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imgUrl", "coilStart")
fun bindImage(
    imgView: ImageView,
    imgUrl: String?,
    start: Boolean
) {
    imgUrl?.let {

        val imgUri =
            imgUrl
                .toUri() // String to URI
                .buildUpon() // allows modifications
                .scheme("https") // forces https protocol
                .build()

        imgView.load(imgUri) {
            // Making empty images look nice
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)

            // Prevent java.lang.IllegalArgumentException:
            // Software rendering doesn't support hardware bitmaps
            allowHardware(false)

            // Smooth image transitions
            if (start) MemoryCache.Key(imgUrl) else placeholderMemoryCacheKey(imgUrl)
        }
    }
}
