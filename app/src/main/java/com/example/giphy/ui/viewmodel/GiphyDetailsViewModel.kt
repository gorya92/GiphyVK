package com.example.giphy.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.giphy.data.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GiphyDetailsViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    fun getGiphyDetail(id: String): LiveData<com.example.giphy.data.model.GiphyDetails.Data> {
        return liveData {
            emit(repository.getGiphyDetails(id))
        }
    }

}
