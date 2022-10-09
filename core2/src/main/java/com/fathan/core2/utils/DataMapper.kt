package com.fathan.core2.utils

import com.fathan.core2.data.source.local.entity.UserEntity
import com.fathan.core2.data.source.remote.response.ListUserResponse
import com.fathan.core2.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponsesToDomain(input: List<ListUserResponse>): Flow<List<User>>{
        val dataArray = ArrayList<User>()
        input.map {
            val user = User(
                it.id,
                it.login,
                it.url,
                it.avatarUrl,
                it.name,
                it.location,
                it.type,
                it.publicRepos,
                it.followers,
                it.following,
                false
            )
            dataArray.add(user)
        }
        return flowOf(dataArray)
    }

    fun mapResponseToDomain(input: ListUserResponse): Flow<User>{
        return flowOf(
            User(
                input.id,
                input.login,
                input.url,
                input.avatarUrl,
                input.name,
                input.location,
                input.type,
                input.publicRepos,
                input.followers,
                input.following,
                false
            )
        )
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User>{
        return input.map {
            User(
                it.id,
                it.login,
                it.avatarUrl,
                it.url,
                it.name,
                it.location,
                it.type,
                it.publicRepos,
                it.followers,
                it.following,
                it.isFavorite
            )
        }
    }

    fun mapEntityToDomain(input: UserEntity?): User{
        return User(
                input?.id,
                input?.login,
                input?.avatarUrl,
                input?.url,
                input?.name,
                input?.location,
                input?.type,
                input?.publicRepos,
                input?.followers,
                input?.following,
                input?.isFavorite
            )
    }

    fun mapDomainToEntity(input: User) = UserEntity(
            input.id,
            input.login,
            input.avatarUrl,
            input.url,
            input.name,
            input.location,
            input.type,
            input.publicRepos,
            input.followers,
            input.following,
            input.isFavorite
        )

}