package com.ameliariely.newmacbookwhodis.views

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.TextView

import com.ameliariely.newmacbookwhodis.R

class StoryViewHolder(itemView: View) : ViewHolder(itemView) {

    val descriptionTextView: TextView
    val nameTextView: TextView

    init {
        nameTextView = itemView.findViewById(R.id.textview_name)
        descriptionTextView = itemView.findViewById(R.id.textview_description)
    }
}
