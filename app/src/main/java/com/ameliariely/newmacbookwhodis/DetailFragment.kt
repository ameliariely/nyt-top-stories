package com.ameliariely.newmacbookwhodis

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ameliariely.newmacbookwhodis.models.NYTResult
import com.ameliariely.newmacbookwhodis.models.NYTStory
import com.ameliariely.newmacbookwhodis.util.NYTRequest
import kotlinx.android.synthetic.main.frag_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Main UI for the task detail screen.
 */
class DetailFragment : Fragment() {

    private lateinit var detailTitle: TextView

    private lateinit var detailDescription: TextView

    private lateinit var detailSwtich: TextView

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val topStoriesCall = NYTRequest().getTopStoriesNYTResult(resources)
        val uiHandler = Handler()
        var articleList: List<NYTStory>?
        topStoriesCall.enqueue(object : Callback<NYTResult> {
            override fun onFailure(call: Call<NYTResult>?, t: Throwable?) {
                Log.e("Retrofit failure", call.toString(), t)
                uiHandler.post({ Snackbar.make(rootView, "Uh oh, something went wrong.", Snackbar.LENGTH_SHORT).show() })
            }

            override fun onResponse(call: Call<NYTResult>?, response: Response<NYTResult>?) {
                uiHandler.post(
                        {
                            articleList = response?.body()?.results
                            val firstArticle = articleList?.first()
                            textView.text = firstArticle?.title ?: "No title"
                            textView1.text = firstArticle?.abstract ?: "No abstract"
                        }
                )
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.frag_detail, container, false)
        with(rootView) {
            detailTitle = findViewById(R.id.textView)
            detailDescription = findViewById(R.id.textView1)
            detailSwtich = findViewById(R.id.switch1)
        }

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        private val ARGUMENT_TASK_ID = "TASK_ID"

        fun newInstance(taskId: String?) =
                DetailFragment().apply {
                    arguments = Bundle().apply { putString(ARGUMENT_TASK_ID, taskId) }
                }
    }

}
