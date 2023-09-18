package com.slava_110.kotlinpractices.di

import androidx.work.WorkManager
import com.slava_110.kotlinpractices.model.ImageDownloader
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.dsl.onClose

fun commonModule(): Module =
    module {
        single<ImageDownloader> { ImageDownloader() } onClose { it?.close() }
        factory<WorkManager> { WorkManager.getInstance(get()) }
    }