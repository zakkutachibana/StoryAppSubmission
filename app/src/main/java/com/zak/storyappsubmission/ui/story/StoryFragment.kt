package com.zak.storyappsubmission.ui.story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zak.storyappsubmission.*
import com.zak.storyappsubmission.databinding.FragmentStoryBinding
import com.zak.storyappsubmission.ui.main.MainActivity

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val storyViewModel: StoryViewModel by viewModels {
        StoryViewModel.StoryViewModelFactory((activity as MainActivity).getToken())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvStories.layoutManager = layoutManager

        getData()
        return binding.root
    }

    private fun getData() {
        val adapter = ListStoryAdapter()
        binding.rvStories.adapter = adapter
        storyViewModel.listStory.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }



}
