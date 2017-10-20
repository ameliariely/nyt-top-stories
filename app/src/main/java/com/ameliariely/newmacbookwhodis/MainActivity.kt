package com.ameliariely.newmacbookwhodis

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.ameliariely.newmacbookwhodis.models.NYTResult
import com.ameliariely.newmacbookwhodis.util.NYTRequest
import com.ameliariely.newmacbookwhodis.views.StoryRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))


        val topStoriesCall = NYTRequest().getTopStoriesNYTResult(resources)
        val uiHandler = Handler()
        topStoriesCall.enqueue(object : Callback<NYTResult> {
            override fun onFailure(call: Call<NYTResult>?, t: Throwable?) {
                Log.e("Retrofit failure", call.toString(), t)
                uiHandler.post({ Snackbar.make(rootView, "Uh oh, something went wrong.", Snackbar.LENGTH_SHORT).show() })
            }

            override fun onResponse(call: Call<NYTResult>?, response: Response<NYTResult>?) {
                uiHandler.post(
                        {
                            val articleList = response?.body()?.results
                            if (articleList == null) {
                                Snackbar.make(rootView, "Uh oh, the results are empty", Snackbar.LENGTH_SHORT).show()
                            } else {
                                recyclerView.adapter = StoryRecyclerViewAdapter(articleList)
                            }
                        }
                )
            }
        })
    }


}
