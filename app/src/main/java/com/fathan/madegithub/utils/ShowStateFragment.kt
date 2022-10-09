package com.fathan.madegithub.utils

import com.fathan.madegithub.databinding.FragmentFavoriteBinding
import com.fathan.madegithub.databinding.FragmentFollowBinding
import com.fathan.madegithub.databinding.FragmentHomeBinding

interface ShowStateFragment {
    fun onSuccessState(homeBinding: FragmentHomeBinding? = null,
                        followBinding: FragmentFollowBinding? =null,
                        favoriteBinding: FragmentFavoriteBinding? = null)

    fun onLoadingState(homeBinding: FragmentHomeBinding? = null,
                       followBinding: FragmentFollowBinding? =null,
                       favoriteBinding: FragmentFavoriteBinding? = null)


    fun onErrorState(homeBinding: FragmentHomeBinding? = null,
                       followBinding: FragmentFollowBinding? =null,
                       favoriteBinding: FragmentFavoriteBinding? = null,
                        message:String?)
}