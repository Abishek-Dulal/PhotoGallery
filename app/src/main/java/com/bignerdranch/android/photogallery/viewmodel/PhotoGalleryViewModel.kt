package com.bignerdranch.android.photogallery.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.photogallery.Model.GalleryItem
import com.bignerdranch.android.photogallery.flickr.FlickrFetchr

class PhotoGalleryViewModel(private val app: Application):ViewModel(){
    val galleryItemLiveData: LiveData<List<GalleryItem>>
    private val flickrFetchr = FlickrFetchr()
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        galleryItemLiveData = Transformations.switchMap(mutableSearchTerm){searchTerm->
            if (searchTerm.isBlank()) {
                flickrFetchr.searchPhotos(" ")
            } else {
                flickrFetchr.searchPhotos(searchTerm)
            }
        }
    }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }

}