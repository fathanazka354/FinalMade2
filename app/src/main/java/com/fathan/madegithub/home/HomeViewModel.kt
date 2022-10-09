package com.fathan.madegithub.home

import androidx.lifecycle.*
import com.fathan.core2.data.Resource
import com.fathan.core2.domain.model.User
import com.fathan.core2.domain.usecase.UserUseCase

class HomeViewModel(private val userUseCase: UserUseCase):ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    fun setSearch(query: String){
        if (username.value == query){
            return
        }
        username.value = query
    }

    val users:LiveData<Resource<List<User>>> = Transformations.switchMap(username){
        userUseCase.getAllUser(it).asLiveData()
    }
}