package com.bangkit.coldswiftapps.data.remote

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("token")
	val token: String? = null
)
