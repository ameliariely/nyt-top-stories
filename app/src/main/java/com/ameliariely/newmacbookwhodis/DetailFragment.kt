package com.ameliariely.newmacbookwhodis

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ameliariely.newmacbookwhodis.util.NYTRequest


/**
 * Main UI for the task detail screen.
 */
class DetailFragment : Fragment() {

    private lateinit var detailTitle: TextView

    private lateinit var detailDescription: TextView

    private lateinit var detailSwtich: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NYTRequest().getTopStoriesHome(resources, onSucccessFunction = {

        }, onFailureFunction = {

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

        fun newInstance(taskId: String?) =
                DetailFragment().apply {
                    arguments = Bundle().apply { putString(ARGUMENT_TASK_ID, taskId) }
                }
    }

}
