/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ameliariely.newmacbookwhodis

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ameliariely.newmacbookwhodis.models.NYTResult
import com.ameliariely.newmacbookwhodis.util.NYTService
import kotlinx.android.synthetic.main.frag_detail.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Main UI for the task detail screen.
 */
class DetailFragment : Fragment() {

    private lateinit var detailTitle: TextView

    private lateinit var detailDescription: TextView

    private lateinit var detailSwtich: TextView

    private val httpClient: OkHttpClient = OkHttpClient()

    //TODO halp
    private val NYT_BASE_URL = "https://api.nytimes.com/svc/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        httpClient.interceptors().add(object : Interceptor {
//            @Throws(IOException::class)
//            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//                val original = chain.request()
//                val originalHttpUrl = original.url()
//
//                val url = originalHttpUrl.newBuilder()
//                        .addQueryParameter("api-key", getString(R.string.nyt_top_stories_api_key))
//                        .build()
//
//                // Request customization: add request headers
//                val requestBuilder = original.newBuilder()
//                        .url(url)
//
//                val request = requestBuilder.build()
//                return chain.proceed(request)
//            }
//        })

        val retrofit = Retrofit.Builder()
                .baseUrl(NYT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create<NYTService>(NYTService::class.java)

        val call = service.storiesForSection("home")
        val uiHandler = Handler()

        call.enqueue(object : Callback<NYTResult>{

            override fun onFailure(call: Call<NYTResult>?, t: Throwable?) {
                Log.e("amelia", call.toString(), t)
                uiHandler.post(
                        {
                            textView1.height = 5000
                            textView1.width = 2000
                            textView1.text = "Error" +t?.stackTrace}
                )
            }

            override fun onResponse(call: Call<NYTResult>?, response: Response<NYTResult>?) {
                uiHandler.post(
                        { textView.text = response?.body()?.copyright ?: "No copyright" }
                )
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.frag_detail, container, false)
        with(root) {
            detailTitle = findViewById(R.id.textView)
            detailDescription = findViewById(R.id.textView1)
            detailSwtich = findViewById(R.id.switch1)
        }

        return root
    }

    companion object {

        private val ARGUMENT_TASK_ID = "TASK_ID"

        private val REQUEST_EDIT = 1

        fun newInstance(taskId: String?) =
                DetailFragment().apply {
                    arguments = Bundle().apply { putString(ARGUMENT_TASK_ID, taskId) }
                }
    }

}
