package com.fathan.madegithub.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathan.core2.data.Resource
import com.fathan.core2.ui.UserAdapter
import com.fathan.madegithub.R
import com.fathan.madegithub.databinding.FragmentFavoriteBinding
import com.fathan.madegithub.databinding.FragmentFollowBinding
import com.fathan.madegithub.databinding.FragmentHomeBinding
import com.fathan.madegithub.utils.ShowStateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : Fragment(), ShowStateFragment {

    companion object{
        fun newInstance(username:String, type: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME,username)
                    putString(TYPE, type)
                }
            }
        private const val USERNAME = "username"
        private const val TYPE = "type"
    }

    private lateinit var followBinding: FragmentFollowBinding
    private lateinit var followAdapter: UserAdapter
    private lateinit var username: String
    private var type: String? = null
    private val followViewModel: FollowViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME).toString()
            type = it.getString(TYPE)
            Log.d("FollowFragment", "onCreate: $username + $type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followBinding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return followBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followAdapter = UserAdapter(arrayListOf()) {user,_ ->
            Toast.makeText(context, user, Toast.LENGTH_SHORT).show()
        }

        followBinding.recyclerFollow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = followAdapter
        }

        when(type){
            resources.getString(R.string.followers) -> {
                followViewModel.setFollow(username, TypeView.FOLLOWER)
            }
            resources.getString(R.string.following) -> {
                followViewModel.setFollow(username, TypeView.FOLLOWING)
            }
            else -> {
                onErrorState(followBinding = followBinding, message = null)
            }
        }
        observeFollow()
    }

    private fun observeFollow(){
        followViewModel.favoriteUsers.observe(viewLifecycleOwner){
            it.let {
                when(it){
                    is Resource.Success -> {
                        if (!it.data.isNullOrEmpty()){
                            onSuccessState(followBinding= followBinding)
                            followAdapter.run { setList(it.data) }
                        }else{
                            onErrorState(followBinding = followBinding, message = resources.getString(R.string.not_have,username,type))
                        }
                    }
                    is Resource.Loading -> {
                        onLoadingState(followBinding = followBinding)
                    }
                    is Resource.Error -> {
                        onErrorState(followBinding = followBinding, message = it.message)
                    }
                }
            }
        }
    }


    override fun onSuccessState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?
    ) {
        followBinding?.apply {
            errLayout.errorFragment.visibility = View.GONE
            recyclerFollow.visibility = View.VISIBLE
        }
    }

    override fun onLoadingState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?
    ) {
        followBinding?.apply {
            errLayout.errorFragment.visibility = View.GONE
            recyclerFollow.visibility = View.GONE
        }
    }

    override fun onErrorState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?,
        message: String?
    ) {
        followBinding?.apply {
            errLayout.apply {
                errorFragment.visibility = View.VISIBLE
                emptyText.text = message?: resources.getString(R.string.not_found)
            }
            recyclerFollow.visibility = View.GONE
        }
    }

}