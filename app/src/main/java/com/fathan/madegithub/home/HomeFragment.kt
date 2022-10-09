package com.fathan.madegithub.home

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fathan.core2.data.Resource
import com.fathan.core2.ui.UserAdapter
import com.fathan.madegithub.R
import com.fathan.madegithub.databinding.FragmentFavoriteBinding
import com.fathan.madegithub.databinding.FragmentFollowBinding
import com.fathan.madegithub.databinding.FragmentHomeBinding
import com.fathan.madegithub.utils.ShowStateFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class HomeFragment : Fragment(), ShowStateFragment {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeAdapter: UserAdapter
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var menuItem: MenuItem
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.home)
        homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding.errorLayout.emptyText.text = getString(R.string.not_found)

        homeAdapter = UserAdapter(arrayListOf()) { username, iv ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(username),
                FragmentNavigatorExtras(iv to username)
            )
        }

        homeBinding.rvUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeAdapter
        }

        observeData()
    }

    private fun observeData() {
        homeViewModel.users.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        onSuccessState(homeBinding)

                        it.data?.apply {
                            homeAdapter.setList(this)
                        }
                    }
                    is Resource.Error -> {
                        onErrorState(homeBinding, message = it.message)
                    }
                    is Resource.Loading -> {
                        onLoadingState(homeBinding)
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
        homeBinding?.apply {
            errorLayout.errorFragment.visibility = View.GONE
            progressBar.visibility = View.GONE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setProgress(100, false)
            }
            rvUser.visibility = View.VISIBLE
            resources
        }
    }

    override fun onLoadingState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?
    ) {
        homeBinding?.apply {
            errorLayout.errorFragment.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setProgress(100, true)
            }
            rvUser.visibility = View.GONE
        }
    }

    override fun onErrorState(
        homeBinding: FragmentHomeBinding?,
        followBinding: FragmentFollowBinding?,
        favoriteBinding: FragmentFavoriteBinding?,
        message: String?
    ) {
        homeBinding?.apply {
            errorLayout.apply {
                errorFragment.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                if (message == null) {
                    emptyText.text = getString(R.string.not_found)
                    ivClose.setImageResource(R.drawable.ic_close)
                } else {
                    emptyText.text = getString(R.string.not_found)
                    ivClose.setImageResource(R.drawable.ic_close)
                }
                rvUser.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_nav_bar,menu)
        menuItem = menu.findItem(R.id.search_user)
        searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setIconifiedByDefault(true)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeViewModel.setSearch(query = query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.setSearch(query = newText.toString())
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

}