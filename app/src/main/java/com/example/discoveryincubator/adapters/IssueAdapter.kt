package com.example.discoveryincubator.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.discoveryincubator.R
import com.example.discoveryincubator.models.Issue
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_issue.view.*

class IssueAdapter(private val context: Context, private val issues: List<Issue>) :
    RecyclerView.Adapter<IssueAdapter.ViewHolder>() {

    private val TAG: String = IssueAdapter::class.java.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_issue, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    override fun getItemCount() = issues.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(issue: Issue) {
            itemView.tvTitle.text = issue.title
            itemView.tvPublicationDate.text = issue.publicationDate
            itemView.tvPublisher.text = issue.publisher

            itemView.ivCover.contentDescription = "Image of ${issue.title}"
            Picasso.get()
                .load(issue.thumbnail.pathIncludingExtension)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.image_not_available)
                .into(itemView.ivCover, object : Callback {
                    override fun onSuccess() {
                        Log.i(TAG, "Image loaded")
                    }

                    override fun onError(e: Exception?) {
                        Log.i(TAG, "There was an error: $e")
                    }
                })
        }
    }
}