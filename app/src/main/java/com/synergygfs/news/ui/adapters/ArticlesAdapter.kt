package com.synergygfs.news.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synergygfs.news.data.Article
import com.synergygfs.news.databinding.ItemArticleBinding
import java.util.*

class ArticlesAdapter(
    private var articlesCollection: Vector<Article>
) :
    RecyclerView.Adapter<ArticlesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemArticleBinding =
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = articlesCollection[position]
        val itemView = holder.itemView

        // Set the title
        holder.binding?.title?.text = article.title

        Log.d("binding", article.title.toString())

        // Set the thumbnail
        holder.binding?.thumbnail?.let {
            Glide.with(holder.itemView.context)
                .load(article.urlToImage)
                .centerCrop()
                .into(it)
        }
//
//        itemView.setOnClickListener {
//            itemViewListeners.onClick(vacation.id)
//        }
//
//        itemView.setOnLongClickListener {
//            itemViewListeners.onLongClick(vacation)
//            true
//        }
    }

    class MyViewHolder(binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemArticleBinding? = binding
    }

    override fun getItemCount() = articlesCollection.size
}
