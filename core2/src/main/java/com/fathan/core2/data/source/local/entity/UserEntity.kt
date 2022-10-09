package com.fathan.core2.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity (
    @PrimaryKey
    @ColumnInfo(name = "id_user")
    var id: Int?,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String?,

    @ColumnInfo(name = "html_url")
    var url: String?,

    @ColumnInfo(name = "name_user")
    var name: String?,

    @ColumnInfo(name = "location")
    var location: String?,

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "public_repository")
    var publicRepos: Int?,

    @ColumnInfo(name = "followers")
    var followers: Int?,

    @ColumnInfo(name = "following")
    var following: Int?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean?,
)