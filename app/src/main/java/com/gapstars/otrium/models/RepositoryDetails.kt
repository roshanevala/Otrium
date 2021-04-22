package com.gapstars.otrium.models

data class RepositoryDetails(
    val id: String,
    val name: String,
    val description: String?,
    val login: String,
    val avatarUrl: String,
    val stargazerCount: String,
    val primaryLanguage: String

)