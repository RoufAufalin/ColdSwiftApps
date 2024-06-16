package com.bangkit.coldswiftapps.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllTicketResponse(
	@field:SerializedName("AllTiketResponse")
	val allTicketResponse: List<AllTicketResponseItem?>? = null
)

data class AllTicketResponseItem(

	@field:SerializedName("eventId")
	val eventId: Int? = null,

	@field:SerializedName("purchasedAt")
	val purchasedAt: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("ticketId")
	val ticketId: String? = null
)
