package com.fathan.favorite.di

import com.fathan.core2.domain.usecase.UserInteractor
import com.fathan.core2.domain.usecase.UserUseCase
import com.fathan.favorite.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory <UserUseCase>{ UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel{ FavoriteViewModel(get()) }
}