package com.synergygfs.news.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.synergygfs.news.R
import com.synergygfs.news.databinding.FragmentTagsBinding

class TagsFragment: Fragment() {
    private lateinit var binding: FragmentTagsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tags, container, false
        )

        return binding.root
    }
}