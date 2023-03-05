package com.example.giphy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.giphy.data.api.ApiServices
import com.example.giphy.data.paging.GiphyPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository
@Inject constructor(private val apiServices: ApiServices) {

    fun getSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { GiphyPagingSource(apiServices, query) }
    ).liveData

    suspend fun getGiphyDetails(id: String): com.example.giphy.data.model.GiphyDetails.Data {
        return apiServices.getDetailGiphy(id).body()!!.data
    }
}