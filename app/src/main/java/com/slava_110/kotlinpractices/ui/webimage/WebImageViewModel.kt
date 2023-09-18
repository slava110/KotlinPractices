package com.slava_110.kotlinpractices.ui.webimage

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.work.WorkManager
import com.slava_110.kotlinpractices.R
import com.slava_110.kotlinpractices.model.ImageDownloader
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WebImageViewModel: ViewModel(), KoinComponent {
    private val workManager: WorkManager by inject()
    private val imageDownloader: ImageDownloader by inject()

    private val image = workManager
        .getWorkInfosForUniqueWorkLiveData("downloadImage")
        .map { it.firstOrNull()?.outputData?.getByteArray("image") }

    val imageBitmap = image.map {
        it?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        } ?: IMAGE_PLACEHOLDER
    }

    fun loadImage(imageUrl: String) {
        imageDownloader.downloadImage(imageUrl)
    }

    companion object {
        private val IMAGE_PLACEHOLDER = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.image_placeholder)
    }
}