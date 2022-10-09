package com.fathan.madegithub.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fathan.core2.data.Resource
import com.fathan.core2.domain.model.User
import com.fathan.madegithub.R
import com.fathan.madegithub.databinding.FragmentDetailBinding
import com.fathan.madegithub.follow.FollowFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var user: User
    private var isFavorite = false
    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = args.username
        detailBinding = FragmentDetailBinding.inflate(layoutInflater, container,false)
        detailBinding.lifecycleOwner = viewLifecycleOwner
        observeDetail()
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabList = arrayOf(resources.getString(R.string.followers), resources.getString(R.string.following))
        pagerAdapter = PagerAdapter(tabList,args.username,this)
        detailBinding.viewPagerDetailProfile.adapter = pagerAdapter

        TabLayoutMediator(detailBinding.tabLayout,detailBinding.viewPagerDetailProfile){tab, position->
            tab.text = tabList[position]
        }.attach()
    }

    private fun observeDetail(){
        detailViewModel.getDetailUser(args.username).observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    user = it.data!!
                    detailBinding.data = it.data
                    detailBinding.pgDetail.visibility = View.GONE
                    detailViewModel.getDetailState(args.username)?.observe(
                        viewLifecycleOwner
                    ){ user ->
                        isFavorite = user.isFavorite == true
                        changedFavorite(isFavorite)
                    }
                    detailBinding.fabFavorite.show()
                }
                is Resource.Error ->{
                    detailBinding.fabFavorite.hide()
                    detailBinding.pgDetail.visibility= View.GONE
                }
                is Resource.Loading -> {
                    detailBinding.pgDetail.visibility = View.VISIBLE
                    detailBinding.fabFavorite.hide()
                }
            }
            changedFavorite(isFavorite)
            detailBinding.fabFavorite.setOnClickListener {
                addOrRemoveFavorite()
                changedFavorite(isFavorite)
            }
        }
    }

    private fun addOrRemoveFavorite(){
        if (isFavorite){
            user.isFavorite = !isFavorite
            detailViewModel.deleteFavorite(user)
            Toast.makeText(
                context, getString(R.string.favorite_remove), Toast.LENGTH_SHORT
            ).show()
            isFavorite = !isFavorite
        }else{
            user.isFavorite = !isFavorite
            detailViewModel.insertFavorite(user)
            Toast.makeText(
                context,getString(R.string.favorite_add),Toast.LENGTH_SHORT
            ).show()
            isFavorite = !isFavorite
        }
    }

    private fun changedFavorite(state: Boolean){
        if (state){
            detailBinding.fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
        }else{
            detailBinding.fabFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_unfavorite))
        }
    }

    inner class PagerAdapter(
        private val tabList: Array<String>,
        private val username: String,
        fragment: Fragment
    ):FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(username,tabList[position])
    }
}
}