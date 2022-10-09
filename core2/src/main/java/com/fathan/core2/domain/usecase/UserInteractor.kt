package com.fathan.core2.domain.usecase

import com.fathan.core2.data.Resource
import com.fathan.core2.domain.model.User
import com.fathan.core2.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository):UserUseCase {
    override fun getAllUser(query: String?): Flow<Resource<List<User>>> {
        return userRepository.getAllUser(query)
    }

    override fun getFollowers(username: String): Flow<Resource<List<User>>> {
        return userRepository.getFollowers(username)
    }

    override fun getFollowing(username: String): Flow<Resource<List<User>>> {
        return userRepository.getFollowing(username)
    }

    override fun getDetailUser(username: String): Flow<Resource<User>> {
        return userRepository.getDetailUser(username)
    }

    override fun getFavoriteUser(): Flow<List<User>> {
        return userRepository.getFavoriteUser()
    }

    override fun getDetailState(username: String): Flow<User>? {
        return userRepository.getDetailState(username)
    }

    override suspend fun insertUser(user: User) {
        userRepository.insertUser(user)
    }

    override suspend fun deleteUser(user: User): Int {
        return userRepository.deleteUser(user)
    }
}