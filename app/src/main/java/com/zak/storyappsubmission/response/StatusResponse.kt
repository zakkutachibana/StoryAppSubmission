package com.zak.storyappsubmission.response

import com.google.gson.annotations.SerializedName

data class StatusResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
