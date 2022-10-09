package com.fathan.madegithub.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fathan.core2.domain.model.User
import com.fathan.core2.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val useCase: UserUseCase):ViewModel() {
    fun getDetailUser(username: String) = useCase.getDetailUser(username).asLiveData()

    fun getDetailState(username: String) = useCase.getDetailState(username)?.asLiveData()

    fun insertFavorite(user: User) = viewModelScope.launch {
        useCase.insertUser(user)
    }

    fun deleteFavorite(user: User) = viewModelScope.launch {
        useCase.deleteUser(user)
    }
}