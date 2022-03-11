package com.synergygfs.news.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synergygfs.news.R
import com.synergygfs.news.data.Article
import com.synergygfs.news.databinding.ItemArticleBinding
import java.util.*

interface ItemViewListeners {
    fun onClick(article: Article)
}

class ArticlesAdapter(
    private var articlesCollection: Vector<Article>,
    private val itemViewListeners: ItemViewListeners
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

        holder.itemView.setOnClickListener {
            itemViewListeners.onClick(article)
        }

        // Set the title
        holder.binding?.title?.text = article.title

        // Set the thumbnail
        holder.binding?.thumbnail?.let {
            Glide.with(holder.itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.default_thumbnail)
                .centerCrop()
                .into(it)
        }
    }

    class MyViewHolder(binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemArticleBinding? = binding
    }

    override fun getItemCount() = articlesCollection.size
}
