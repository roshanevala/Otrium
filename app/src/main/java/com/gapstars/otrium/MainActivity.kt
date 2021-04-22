package com.gapstars.otrium

import GithubQuery
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gapstars.otrium.models.RepositoryDetails
import com.gapstars.otrium.ui.GithubAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    val pinnedRepositoryList: MutableList<RepositoryDetails> = ArrayList()
    val topRepositoryList: MutableList<RepositoryDetails> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.githubData.observe(this, Observer { githubData ->

            for (i in 0..(githubData.profile()?.pinnedItems()?.nodes()?.size?.minus(1) ?: 0)) {
                var id = (githubData.profile()?.pinnedItems()?.nodes()
                    ?.get(i) as GithubQuery.AsRepository).id()
                var name = (githubData.profile()?.pinnedItems()?.nodes()
                    ?.get(i) as GithubQuery.AsRepository).name()
                var desc = (githubData.profile()?.pinnedItems()?.nodes()
                    ?.get(i) as GithubQuery.AsRepository).description()
                var ownerLogin = (githubData.profile()?.pinnedItems()?.nodes()
                    ?.get(i) as GithubQuery.AsRepository).owner().login()
                var avatar = (githubData.profile()?.pinnedItems()?.nodes()
                    ?.get(i) as GithubQuery.AsRepository).owner().avatarUrl().toString()
                val rep = RepositoryDetails(id, name, desc, ownerLogin, avatar, "", "")

                pinnedRepositoryList.add(rep)
            }


            recycler_view_pinned.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = GithubAdapter(pinnedRepositoryList)
            }

            recycler_view_top.also {
                val mLayoutManager = LinearLayoutManager(applicationContext)
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

                it.layoutManager = mLayoutManager
                it.setHasFixedSize(true)
                it.adapter = GithubAdapter(pinnedRepositoryList)
            }

            recycler_view_starred.also {
                val mLayoutManager = LinearLayoutManager(applicationContext)
                mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

                it.layoutManager = mLayoutManager
                it.setHasFixedSize(true)
                it.adapter = GithubAdapter(pinnedRepositoryList)
            }

            refreshUI(githubData)


        })
        viewModel.setUserId("benbalter")

    }

    private fun refreshUI(data: GithubQuery.Data) {
        text_name.text = data.profile()?.name()
        text_login.text = data.profile()?.login()
        text_email.text = data.profile()?.email()

        val followersCount = data.profile()?.followers()?.totalCount()
        text_followers.text = "$followersCount followers"
        val followingCount = data.profile()?.following()?.totalCount()
        text_following.text = "$followingCount following"

        val url = data.profile()?.avatarUrl()
        Glide.with(image_avatar)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(image_avatar)
    }

}

