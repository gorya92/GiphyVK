package com.example.giphy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.giphy.data.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val giphy = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString)
            .cachedIn(viewModelScope)
    }

    fun searchGiphy(query: String) {
        currentQuery.value = query
    }


    companion object {
        private const val DEFAULT_QUERY = "cats"
    }
}