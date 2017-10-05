package com.ameliariely.newmacbookwhodis

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ameliariely.newmacbookwhodis.util.replaceFragmentInActivity;

/**
 * Created by amelia on 10/3/17.
 */

class ViewDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        // Get the requested task id
        val taskId = intent.getStringExtra(EXTRA_TASK_ID)

        val taskDetailFragment = supportFragmentManager
                .findFragmentById(R.id.contentFrame) as DetailFragment? ?:
                DetailFragment.newInstance(taskId).also {
                    replaceFragmentInActivity(it, R.id.contentFrame)
                }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}


