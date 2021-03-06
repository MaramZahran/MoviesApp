package com.yassir.moviesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yassir.moviesapp.data.MoviesList
import com.yassir.moviesapp.data.Resource
import com.yassir.moviesapp.network.repo.ConfigurationRepoImp
import com.yassir.moviesapp.network.repo.MoviesListRepo
import com.yassir.moviesapp.network.repo.MoviesListRepoImp
import kotlinx.coroutines.launch

class MoviesViewModel() : ViewModel() {

    //TODO change the way of inilaization
    private val moviesRepo: MoviesListRepo = MoviesListRepoImp()
    private val _uiStatus = MutableLiveData<Resource<MoviesList>>(Resource.loading(null))
    val uiStatus: LiveData<Resource<MoviesList>> = _uiStatus

    fun getMovies() {
        viewModelScope.launch {
            try {
                ConfigurationRepoImp.ImageConfiguration.initConfiguration()
                _uiStatus.value = Resource.success(moviesRepo.fetchMovies())
            } catch (e: Exception) {
                _uiStatus.value = Resource.error(e.localizedMessage.toString(), null)
            }
        }
    }


}