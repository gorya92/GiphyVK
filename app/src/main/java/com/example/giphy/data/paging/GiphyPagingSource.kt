package com.example.giphy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.giphy.data.api.ApiServices
import com.example.giphy.data.model.GiphyList
import okio.IOException
import retrofit2.HttpException

private const val STARTING_PAGE = 1

class GiphyPagingSource(
    private val apiServices: ApiServices,
    private val query: String
) : PagingSource<Int, GiphyList.Data>() {
    override fun getRefreshKey(state: PagingState<Int, GiphyList.Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GiphyList.Data> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val response =
                apiServices.getGiphyList(query, params.loadSize, position * params.loadSize)
            val data = response.body()!!.data

            LoadResult.Page(
                data = data,
                prevKey = if (position == STARTING_PAGE) null else position.minus(1),
                nextKey = if (data.isEmpty()) null else position.plus(1)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }


    }
}