package com.slava_110.kotlinpractices.model

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImageDownloader: AutoCloseable, KoinComponent {
    private val workManager: WorkManager by inject()
    private val client = HttpClient(CIO)

    fun downloadImage(imageUrl: String) {
        workManager
            .enqueueUniqueWork(
                "downloadImage",
                ExistingWorkPolicy.REPLACE,
                downloadImageRequest(imageUrl)
            )
    }

    private fun downloadImageRequest(imageUrl: String) =
        OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .setExpedited(OutOfQuotaPolicy.DROP_WORK_REQUEST)
            .setInputData(workDataOf(
                "url" to imageUrl
            ))
            .build()

    override fun close() {
        client.close()
    }

    inner class DownloadImageWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
        val notificationManager = appContext.getSystemService<NotificationManager>()
        var progress: Pair<Long, Long>

        override suspend fun doWork(): Result =
            withContext(Dispatchers.IO) {
                val image = client.get(inputData.getString("url")!!) {
                    accept(ContentType.Image.Any)
                    onDownload { bytesSentTotal, contentLength ->
                        progress = bytesSentTotal to contentLength
                    }
                }.readBytes()

                Result.success(workDataOf(
                    "image" to image
                ))
            }

        override suspend fun getForegroundInfo(): ForegroundInfo =
            ForegroundInfo()

        fun notification(): Notification =
            NotificationCompat.Builder(applicationContext, )

        private fun createForegroundInfo(): ForegroundInfo {
            val id = applicationContext.getString(R.string.notification_channel_id)
            val title = applicationContext.getString(R.string.notification_title)
            val cancel = applicationContext.getString(R.string.cancel_download)
            // This PendingIntent can be used to cancel the worker
            val intent = WorkManager.getInstance(applicationContext)
                .createCancelPendingIntent(getId())

            val notification = NotificationCompat.Builder(applicationContext, id)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(progress)
                .setSmallIcon(R.drawable.ic_work_notification)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build()

            return ForegroundInfo(notificationId, notification)
        }
    }
}