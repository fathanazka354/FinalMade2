package com.fathan.core2.data.source.remote.network

import com.fathan.core2.data.source.remote.response.ListUserResponse
import com.fathan.core2.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") q: String?
    ): UserResponse

    @GET("users/{username}")
    suspend fun userDetail(
        @Path("username") username: String?
    ): ListUserResponse

    @GET("users/{username}/followers")
    suspend fun userFollower(
        @Path("username") username: String?
    ): List<ListUserResponse>

    @GET("users/{username}/following")
    suspend fun userFollowing(
        @Path("username") username: String?
    ): List<ListUserResponse>

}