package com.bangkit.coldswiftapps.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.coldswiftapps.data.remote.response.AllTicketResponseItem
import com.bangkit.coldswiftapps.databinding.TiketItemBinding
import com.bangkit.coldswiftapps.utils.DateFormatter
import java.util.TimeZone

class TiketAdapter(var tickets: List<AllTicketResponseItem?>?) :
RecyclerView.Adapter<TiketAdapter.ViewHolder>(){
    class ViewHolder(var binding: TiketItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TiketAdapter.ViewHolder {
        val binding = TiketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TiketAdapter.ViewHolder, position: Int) {
        val tickets = tickets?.get(position)
        with(holder.binding){
            tvEventName.text = tickets?.eventId.toString()
            tvVenue.text = tickets?.userName
            date.text= DateFormatter.formatDate(tickets?.purchasedAt!!,  TimeZone.getDefault().id)
        }
    }

    override fun getItemCount(): Int {
        return tickets?.size !!
    }
}