package com.ameliariely.newmacbookwhodis.util

import android.content.res.Resources
import com.ameliariely.newmacbookwhodis.R
import com.ameliariely.newmacbookwhodis.models.NYTResult
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class NYTRequest {

    private val NYT_BASE_URL = "https://api.nytimes.com/svc/"

    fun getTopStoriesNYTResult(res: Resources): Call<NYTResult> {
        val httpClient = OkHttpClient().newBuilder()

        httpClient.interceptors().add(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api-key", res.getString(R.string.nyt_top_stories_api_key))
                        .build()

                // Request customization: add request headers
                val requestBuilder = original.newBuilder()
                        .url(url)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })

        val retrofit = Retrofit.Builder()
                .baseUrl(NYT_BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<NYTService>(NYTService::class.java)

        return service.storiesForSection("home")
    }
}