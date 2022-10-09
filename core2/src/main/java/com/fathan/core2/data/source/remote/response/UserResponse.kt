package com.fathan.core2.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @field:SerializedName("items")
    val items: List<ListUserResponse>
)