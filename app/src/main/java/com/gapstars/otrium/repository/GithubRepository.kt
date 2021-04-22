package com.gapstars.otrium.repository

import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient

object GithubRepository {
    private val BASE_URL = "https://api.github.com/graphql"
    private lateinit var client: ApolloClient


    fun getUser(userId: String): LiveData<GithubQuery.Data> {
        return object : LiveData<GithubQuery.Data>() {
            override fun onActive() {
                super.onActive()
                client = setupApollo()
//            progress_bar.visibility = View.VISIBLE
                client.query(
                    GithubQuery
                        .builder()
                        .query("benbalter")
                        .build()
                )
                    .enqueue(object : ApolloCall.Callback<GithubQuery.Data>() {

                        override fun onFailure(e: ApolloException) {
//                        Log.info(e.message.toString())
                            println(e.message.toString())
                        }

                        override fun onResponse(response: Response<GithubQuery.Data>) {
//                        Log.INFO(" " + response.data()?.repository())
                            value = response.data
                        }

                    })
            }
        }
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
//                    "Authorization", "Bearer " + BuildConfig.AUTH_TOKEN
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