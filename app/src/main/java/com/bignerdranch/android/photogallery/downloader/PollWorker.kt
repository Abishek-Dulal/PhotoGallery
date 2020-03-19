package com.bignerdranch.android.photogallery.downloader

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bignerdranch.android.photogallery.Model.GalleryItem
import com.bignerdranch.android.photogallery.flickr.FlickrFetchr
import com.bignerdranch.android.photogallery.viewmodel.QueryPreferences

private const val TAG = "PollWorker"


class PollWorker(val context: Context,workerParameter:WorkerParameters):Worker(context,workerParameter){
    override fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(context)
        val lastResultId = QueryPreferences.getLastResultId(context)
        val items: List<GalleryItem> = if (query.isEmpty()) {
            FlickrFetchr().fetchPhotosRequest()
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } else {
            FlickrFetchr().searchPhotosRequest(query)
                .execute()
                .body()
                ?.photos
                ?.galleryItems
        } ?: emptyList()

        if (items.isEmpty()) {
            return Result.success()
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "Got an old result: $resultId")
        } else {
            Log.i(TAG, "Got a new result: $resultId")
            QueryPreferences.setLastResultId(context, resultId)
        }


        return Result.success()
    }

}