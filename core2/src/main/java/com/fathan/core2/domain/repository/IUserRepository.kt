package com.fathan.core2.domain.repository

import com.fathan.core2.data.Resource
import com.fathan.core2.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllUser(query: String?): Flow<Resource<List<User>>>
    fun getFollowers(username: String): Flow<Resource<List<User>>>
    fun getFollowing(username: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun getFavoriteUser(): Flow<List<User>>
    fun getDetailState(username: String): Flow<User>?
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User):Int
}