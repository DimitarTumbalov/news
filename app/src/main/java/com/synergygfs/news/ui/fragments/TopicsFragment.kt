package com.synergygfs.news.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synergygfs.news.R
import com.synergygfs.news.UiUtils
import com.synergygfs.news.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {
    private lateinit var binding: FragmentTopicsBinding

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
            inflater, R.layout.fragment_topics, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val readArticlesCount =
            UiUtils.getSharedPrefs(requireContext(), "articles_read_count") ?: "0"

        // Set the read articles counter
        binding.readArticlesCounter.text = getString(R.string.read_articles, readArticlesCount)

        binding.generalBtn.setOnClickListener {
            navigateToArticles("General")
        }

        binding.businessBtn.setOnClickListener {
            navigateToArticles("Business")
        }

        binding.entertainmentBtn.setOnClickListener {
            navigateToArticles("Entertainment")
        }

        binding.healthBtn.setOnClickListener {
            navigateToArticles("Health")
        }

        binding.scienceBtn.setOnClickListener {
            navigateToArticles("Science")
        }

        binding.sportsBtn.setOnClickListener {
            navigateToArticles("Sports")
        }

        binding.technologyBtn.setOnClickListener {
            navigateToArticles("Technology")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun navigateToArticles(topic: String) {
        val action = TopicsFragmentDirections.actionTopicsFragmentToArticlesFragment(topic)
        findNavController().navigate(action)
    }
}