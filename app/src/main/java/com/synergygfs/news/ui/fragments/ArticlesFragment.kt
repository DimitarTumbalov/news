package com.synergygfs.news.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synergygfs.news.R
import com.synergygfs.news.UiUtils
import com.synergygfs.news.data.Article
import com.synergygfs.news.databinding.FragmentArticlesBinding
import com.synergygfs.news.net.API
import com.synergygfs.news.ui.adapters.ArticlesAdapter
import com.synergygfs.news.ui.adapters.ItemViewListeners
import java.util.*


class ArticlesFragment : Fragment(), ItemViewListeners {

    private lateinit var binding: FragmentArticlesBinding

    private val args: ArticlesFragmentArgs by navArgs()

    var adapter: ArticlesAdapter? = null

    private var articlesCollection = Vector<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the ArticlesAdapter
        val articlesRv = binding.articlesRv
        articlesRv.layoutManager = LinearLayoutManager(requireContext())
        adapter = ArticlesAdapter(articlesCollection, this)
        articlesRv.adapter = adapter

        // Register an observer to the adapter so it can update the RecyclerView UI
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)

                binding.noArticlesScreen.isVisible = adapter?.itemCount ?: 0 < 1
            }

            override fun onChanged() {
                super.onChanged()

                binding.noArticlesScreen.isVisible = adapter?.itemCount ?: 0 < 1
            }
        })

        if (articlesCollection.isEmpty())
            loadArticles()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.nav_menu_articles, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.languages -> {
                activity?.findViewById<View?>(item.itemId)?.let {
                    showPopupMenu(it, R.menu.pop_up_menu_languages)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(article: Article) {
        val action = ArticlesFragmentDirections.actionArticlesFragmentToArticleFragment(article)
        findNavController().navigate(action)
    }

    private fun showPopupMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            val data = when (menuItem.itemId) {
                R.id.all -> null
                else -> menuItem.titleCondensed.toString()
            }

            UiUtils.saveSharedPrefs(requireContext(), "articles_language", data)

            // Load articles in the selected language
            loadArticles()

            true
        }

        // Show the popup menu.
        popup.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadArticles() {
        binding.loadingScreen.isVisible = true

        val articlesLanguage = UiUtils.getSharedPrefs(requireContext(), "articles_language")

        API.getArticles(articlesLanguage, args.topic) {
            activity?.runOnUiThread {
                articlesCollection.clear()
                articlesCollection.addAll(it)
                adapter?.notifyDataSetChanged()

                binding.loadingScreen.isVisible = false
            }
        }
    }
}