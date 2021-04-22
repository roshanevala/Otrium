package com.gapstars.otrium

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gapstars.otrium.api.handler.GithubApiHandler
import com.gapstars.otrium.repository.GithubRepository

class MainViewModel : ViewModel() {
    private val _userId: MutableLiveData<String> = MutableLiveData()

    val githubData: LiveData<GithubQuery.Data> = Transformations
        .switchMap(_userId) {
            GithubApiHandler.handleGithubDetails(_userId.value.toString())
            GithubApiHandler.getGithubDetails()
        }

    fun setUserId(userId: String) {
        val update = userId
        if (_userId.value == update) {
            return
        }
        _userId.value = update
    }

}