package com.fathan.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fathan.core2.domain.usecase.UserUseCase

class FavoriteViewModel( useCase: UserUseCase):ViewModel() {
    val favoriteUsers = useCase.getFavoriteUser().asLiveData()
}