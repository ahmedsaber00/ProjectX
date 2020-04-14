package com.afaqy.ptt.presentation.features.channels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.afaqy.ptt.presentation.R
import com.afaqy.ptt.presentation.base.model.ChannelsView
import javax.inject.Inject

class ChannelsAdapter @Inject constructor() : RecyclerView.Adapter<ChannelsAdapter.ViewHolder>() {

    var channels: List<ChannelsView> = arrayListOf()
    var channelListener: ChannelListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return channels.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channel = channels[position]
        holder.channelNameText.text = channel.name
        if (channel.isSelected) {
            holder.channelNameText.setTextColor(
                ContextCompat.getColor(
                    holder.channelNameText.context,
                    android.R.color.black
                )
            )
            holder.clChannelBg.setBackgroundResource(R.drawable.bg_selected_channel_rounded)
        } else {
            holder.channelNameText.setTextColor(
                ContextCompat.getColor(
                    holder.channelNameText.context,
                    android.R.color.white
                )
            )
            holder.clChannelBg.setBackgroundResource(R.drawable.bg_not_selected_channel_rounded)
        }


        holder.itemView.setOnClickListener {
            channels[position].isSelected = !channel.isSelected
            notifyItemChanged(position)
            channelListener?.onChannelClicked(position)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var channelNameText: TextView = view.findViewById(R.id.tvChannel)
        var clChannelBg: ConstraintLayout = view.findViewById(R.id.clChannelBg)

    }

}