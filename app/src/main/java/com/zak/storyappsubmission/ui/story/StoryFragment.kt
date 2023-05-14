package com.zak.storyappsubmission.ui.story

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zak.storyappsubmission.R
import com.zak.storyappsubmission.StoryAdapter
import com.zak.storyappsubmission.databinding.FragmentStoryBinding
import com.zak.storyappsubmission.response.ListStoryItem
import com.zak.storyappsubmission.ui.detail.DetailActivity
import com.zak.storyappsubmission.ui.main.MainActivity

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val storyViewModel by viewModels<StoryViewModel>()
    private lateinit var token: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)

        storyViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(context)
        binding.rvStories.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = (activity as MainActivity).getToken()
        storyViewModel.getStories(token)

        storyViewModel.storyStatus.observe(viewLifecycleOwner) {
            if (!it.error) {
                storyViewModel.story.observe(viewLifecycleOwner) { story ->
                    setStoryList(story)
                }
            }
        }

        storyViewModel.error.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setStoryList(story: List<ListStoryItem>) {
        val listStory = ArrayList<ListStoryItem>()
        for (storyItem in story) {
            listStory.add(storyItem)
        }
        val adapter = StoryAdapter(listStory)
        binding.rvStories.adapter = adapter

        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedStoryDetail(data)
            }
        })
    }

    private fun showSelectedStoryDetail(storyItems: ListStoryItem) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_STORY, storyItems)
        startActivity(intent,
            activity?.let { ActivityOptionsCompat.makeSceneTransitionAnimation(it).toBundle() })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
