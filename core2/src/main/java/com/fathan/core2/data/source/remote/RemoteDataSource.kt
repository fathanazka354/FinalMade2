package com.fathan.core2.data.source.remote

import android.util.Log
import com.fathan.core2.data.source.remote.network.ApiResponse
import com.fathan.core2.data.source.remote.network.ApiService
import com.fathan.core2.data.source.remote.response.ListUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllUsers(query: String?): Flow<ApiResponse<List<ListUserResponse>>> =
        flow {
            try {
                val search = apiService.searchUsers(query)
                val userArray = search.items
                if (userArray.isEmpty()){
                    emit(ApiResponse.Error(null))
                }else{
                    emit(ApiResponse.Success(userArray))
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "error: $e")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowers(username: String?): Flow<ApiResponse<List<ListUserResponse>>> =
        flow {
            try {
                val followers = apiService.userFollower(username)
                emit(ApiResponse.Success(followers))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "error: $e")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getFollowing(username: String?): Flow<ApiResponse<List<ListUserResponse>>> =
        flow {
            try {
                val following = apiService.userFollowing(username)
                emit(ApiResponse.Success(following))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "error: $e")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String): Flow<ApiResponse<ListUserResponse>> =
        flow {
            try {
                val userDetail = apiService.userDetail(username)
                emit(ApiResponse.Success(userDetail))
            }catch (e:Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", "error: $e")
            }
        }.flowOn(Dispatchers.IO)
}