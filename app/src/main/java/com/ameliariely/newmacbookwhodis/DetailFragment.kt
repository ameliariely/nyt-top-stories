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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.frag_detail.*
import okhttp3.*
import java.io.IOException

/**
 * Main UI for the task detail screen.
 */
class DetailFragment : Fragment() {

    private lateinit var detailTitle: TextView

    private lateinit var detailDescription: TextView

    private lateinit var detailSwtich: TextView

    private val httpClient: OkHttpClient = OkHttpClient()

    //TODO halp
    private val NYT_TOPSTORIES_HOME_URL = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=$$$$$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val request = Request.Builder()
                .url(NYT_TOPSTORIES_HOME_URL)
                .build()

        val uiHandler = Handler()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val responseData = response?.body()?.string()
                uiHandler.post(
                        { textView.text = responseData }
                )
            }

            override fun onFailure(call: Call?, e: IOException?) {
                e?.printStackTrace()
                TODO() //To change body of created functions use File | Settings | File Templates.
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
