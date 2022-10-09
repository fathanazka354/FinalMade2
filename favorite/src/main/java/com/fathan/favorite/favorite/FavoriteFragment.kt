package com.fathan.favorite.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathan.core2.ui.UserAdapter
import com.fathan.madegithub.R
import com.fathan.madegithub.databinding.FragmentFavoriteBinding
import com.fathan.madegithub.databinding.FragmentFollowBinding
import com.fathan.madegithub.databinding.FragmentHomeBinding
import com.fathan.madegithub.utils.ShowStateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment(), ShowStateFragment {
    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: UserAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.favorite)
        favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)
        loadKoinModules(com.fathan.favorite.di.viewModelModule)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteAdapter = UserAdapter(arrayListOf()){username, iv->
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(username),
                FragmentNavigatorExtras(iv to username)
            )
        }

        favoriteBinding.recyclerFav.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
        observeFavorite()
    }

    private fun observeFavorite(){
        onLoadingState(favoriteBinding = favoriteBinding)
        favoriteViewModel.favoriteUsers.observe(viewLifecycleOwner){userList ->
            userList.let {
                if (!it.isNullOrEmpty()){
                    onSuccessState(favoriteBinding = favoriteBinding)
                    favoriteAdapter.setList(it)
                }else{
                    onErrorState(
                        favoriteBinding = favoriteBinding,
                        message = resources.getString(R.string.not_have,"",getString(R.string.favorite))
                    )
                }
            }
        }
    }

    override fun onSuccessState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?
    ) {
        favoriteBinding?.apply {
            errLayout.errorFragment.visibility = View.GONE
            recyclerFav.visibility = View.VISIBLE
            pgFavorite.visibility = View.GONE
        }
    }

    override fun onLoadingState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?
    ) {
        favoriteBinding?.apply {
            errLayout.errorFragment.visibility = View.GONE
            recyclerFav.visibility = View.GONE
            pgFavorite.visibility = View.VISIBLE
        }
    }

    override fun onErrorState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?,
        message: String?
    ) {
        favoriteBinding?.apply {
            errLayout.apply {
                errorFragment.visibility = View.VISIBLE
                emptyText.text = message?:resources.getString(R.string.not_found)
            }
            recyclerFav.visibility = View.GONE
            pgFavorite.visibility = View.GONE
        }
    }

}