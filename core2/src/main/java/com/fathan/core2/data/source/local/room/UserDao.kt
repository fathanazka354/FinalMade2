package com.fathan.core2.data.source.local.room

import androidx.room.*
import com.fathan.core2.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY login ASC")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity?)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity): Int

    @Query("SELECT * FROM user_table WHERE login =:username")
    fun getDetailState(username:String): Flow<UserEntity>?
}