package com.fathan.di

import com.fathan.core2.domain.usecase.UserInteractor
import com.fathan.core2.domain.usecase.UserUseCase
import com.fathan.madegithub.detail.DetailViewModel
import com.fathan.madegithub.follow.FollowViewModel
import com.fathan.madegithub.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
val useCaseModule = module {
    factory <UserUseCase>{ UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel{HomeViewModel(get())}
    viewModel{DetailViewModel(get())}
    viewModel{FollowViewModel(get())}
}