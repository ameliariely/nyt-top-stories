package com.ameliariely.newmacbookwhodis.util

import android.content.res.Resources
import android.os.Handler
import android.util.Log
import com.ameliariely.newmacbookwhodis.R
import com.ameliariely.newmacbookwhodis.models.NYTResult
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class NYTRequest {

    private val NYT_BASE_URL = "https://api.nytimes.com/svc/"

    fun getTopStoriesHome(res: Resources, onSucccessFunction: () -> Unit, onFailureFunction: () -> Unit) {
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

        val call = service.storiesForSection("home")
        val uiHandler = Handler()

        call.enqueue(object : Callback<NYTResult> {

            override fun onFailure(call: Call<NYTResult>?, t: Throwable?) {
                Log.e("Retrofit failure", call.toString(), t)
                uiHandler.post({ onFailureFunction() })
            }

            override fun onResponse(call: Call<NYTResult>?, response: Response<NYTResult>?) {
                uiHandler.post({ onSucccessFunction() })
            }
        })
    }
}