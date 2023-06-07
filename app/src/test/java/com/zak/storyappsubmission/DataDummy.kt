package com.zak.storyappsubmission

import com.zak.storyappsubmission.response.ListStoryItem

object DataDummy {
    fun generateDummyStory(): List<ListStoryItem> {
        val storyList: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..5) {
            val story = ListStoryItem(
                "https://raw.githubusercontent.com/zakkutachibana/mediatest/main/jetpack_submission/c_udyr.jpg",
                "2022-02-22T22:22:22Z",
                "Udyr $i",
                "Udyr Base Splash $i",
                "54.5260",
                "$i",
                "15.2551"
            )
            storyList.add(story)
        }
        return storyList
    }
}