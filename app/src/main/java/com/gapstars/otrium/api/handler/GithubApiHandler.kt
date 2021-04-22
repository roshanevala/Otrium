package com.gapstars.otrium.api.handler

import GithubQuery
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import okhttp3.OkHttpClient

object GithubApiHandler {
    private val BASE_URL = "https://api.github.com/graphql"
    private lateinit var client: ApolloClient

    private var itemMutableList: MutableLiveData<GithubQuery.Data> = MutableLiveData()

    fun getGithubDetails(): LiveData<GithubQuery.Data> {
        return itemMutableList
    }

    fun handleGithubDetails(userId: String) {
        client = setupApollo()
//            progress_bar.visibility = View.VISIBLE
        client.query(
            GithubQuery
                .builder()
                .query(userId)
                .build()
        )
            .enqueue(object : ApolloCall.Callback<GithubQuery.Data>() {

                override fun onFailure(e: ApolloException) {
                    Log.i("Fail", e.message.toString())
                }

                override fun onResponse(response: Response<GithubQuery.Data>) {
//                        Log.INFO(" " + response.data()?.repository())
                    itemMutableList.postValue(response.data)

                }

            })
    }

    private fun setupApollo(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(
                    original.method(),
                    original.body()
                )
                builder.addHeader(
                    "Authorization", "Bearer " + "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"
                )
                chain.proceed(builder.build())
            }
            .build()
        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttp)
            .build()
    }
}