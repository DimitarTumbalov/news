package com.synergygfs.news.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.synergygfs.news.R
import com.synergygfs.news.UiUtils
import com.synergygfs.news.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding

    private val args: ArticleFragmentArgs by navArgs()

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
            inflater, R.layout.fragment_article, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If fragment is article isn't recreated, increase the read articles count
        if (savedInstanceState == null) {
            requireContext().let {
                var count = UiUtils.getSharedPrefs(it, "articles_read_count")?.toInt() ?: 0
                count += 1
                UiUtils.saveSharedPrefs(requireContext(), "articles_read_count", count.toString())
            }
        }

        // Get the article from navArgs
        val article = args.article

        // Set the thumbnail
        binding.thumbnail.let {
            Glide.with(requireContext())
                .load(article.urlToImage)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.default_thumbnail)
                .centerCrop()
                .into(it)
        }

        // Set the title
        binding.title.text = article.title

        // Set the source
        article.source?.name.let {
            binding.source.apply {
                if (it?.isNotBlank() == true) {
                    text = getString(R.string.source, it)
                    isVisible = true
                } else
                    isVisible = false
            }
        }

        // Set the date
        article.publishedAt.let {
            binding.publishedAt.apply {
                if (it != null) {
                    text = UiUtils.convertDateToString(it)
                    isVisible = true
                } else
                    isVisible = false
            }
        }

        // Set the description
        article.description.let {
            binding.description.apply {
                if (it?.isNotBlank() == true) {
                    text = it
                    isVisible = true
                } else
                    isVisible = false
            }
        }

        // Set the content
        article.content.let {
            binding.content.apply {
                if (it?.isNotBlank() == true) {
                    text = it
                    isVisible = true
                } else
                    isVisible = false
            }
        }

        // Set the author
        article.author.let {
            binding.author.apply {
                if (it?.isNotBlank() == true) {
                    text = getString(R.string.author, it)
                    isVisible = true
                } else
                    isVisible = false
            }
        }

        // Set the openInWebBtn onClickListener
        binding.openInWebBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(browserIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()

        super.onCreateOptionsMenu(menu, inflater)
    }
}