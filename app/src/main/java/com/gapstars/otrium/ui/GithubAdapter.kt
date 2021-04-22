package com.gapstars.otrium.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gapstars.otrium.R
import com.gapstars.otrium.databinding.RecyclerviewGithubBinding
import com.gapstars.otrium.models.RepositoryDetails

class GithubAdapter(
    private val repositoryDetails: List<RepositoryDetails>
) : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {

    override fun getItemCount() = repositoryDetails.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GithubViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_github,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.recyclerviewGithubBinding.textLogin.text = repositoryDetails[position].login
        holder.recyclerviewGithubBinding.textName.text = repositoryDetails[position].name
        holder.recyclerviewGithubBinding.textDesc.text = repositoryDetails[position].description
        holder.recyclerviewGithubBinding.textStar.text = repositoryDetails[position].stargazerCount
        holder.recyclerviewGithubBinding.textLanguage.text =
            repositoryDetails[position].primaryLanguage
        val url = repositoryDetails[position].avatarUrl
        Glide.with(holder.recyclerviewGithubBinding.imageAvatar)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(holder.recyclerviewGithubBinding.imageAvatar)
    }


    inner class GithubViewHolder(
        val recyclerviewGithubBinding: RecyclerviewGithubBinding
    ) : RecyclerView.ViewHolder(recyclerviewGithubBinding.root)

}