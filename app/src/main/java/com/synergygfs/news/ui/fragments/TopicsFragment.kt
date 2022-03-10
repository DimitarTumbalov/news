package com.synergygfs.news.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synergygfs.news.R
import com.synergygfs.news.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {
    private lateinit var binding: FragmentTopicsBinding

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

        binding.businessBtn.setOnClickListener {
            val action = TopicsFragmentDirections.actionTopicsFragmentToArticlesFragment("business")
            findNavController().navigate(action)
        }
    }
}