package com.ameliariely.newmacbookwhodis.views

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ameliariely.newmacbookwhodis.R
import com.ameliariely.newmacbookwhodis.models.NYTStory


class StoryRecyclerViewAdapter(private val stories: List<NYTStory>) : RecyclerView.Adapter<StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_row, null)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.nameTextView.text = story.title
        holder.descriptionTextView.text = story.abstract
        var color = Color.WHITE
        when (story.subsection) {
            "Opinion" -> color = Color.BLUE
            "N.Y. / Region" -> color = Color.BLUE
            "Politics" -> color = Color.RED
            "World" -> color = Color.GREEN
            "U.S." -> color = Color.GREEN
        }
        holder.itemView.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return stories.size
    }
}
