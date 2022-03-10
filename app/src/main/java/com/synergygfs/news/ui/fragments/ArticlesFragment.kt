package com.synergygfs.news.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.synergygfs.news.R
import com.synergygfs.news.data.Article
import com.synergygfs.news.databinding.FragmentArticlesBinding
import com.synergygfs.news.net.API
import com.synergygfs.news.ui.adapters.ArticlesAdapter
import java.util.*

class ArticlesFragment : Fragment() {

    private lateinit var binding: FragmentArticlesBinding

    private val args: ArticlesFragmentArgs by navArgs()

    var adapter: ArticlesAdapter? = null

    private var articlesCollection = Vector<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_articles, container, false
        )

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the ArticlesAdapter
        val articlesRv = binding.articlesRv
        articlesRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ArticlesAdapter(articlesCollection)
        articlesRv.adapter = adapter

        val topic = args.topic

        API.getArticlesByTopic(topic) {
            activity?.runOnUiThread {
                articlesCollection.addAll(it)
                adapter?.notifyDataSetChanged()
            }
        }
    }
}