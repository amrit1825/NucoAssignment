package com.amrit.bravo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrit.bravo.databinding.MessageItemLayoutBinding
import com.amrit.bravo.model.Message

class MessageAdapter(private val messageList: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    class ViewHolder(val messageItemLayoutBinding: MessageItemLayoutBinding) :
        RecyclerView.ViewHolder(messageItemLayoutBinding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MessageItemLayoutBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.messageItemLayoutBinding.amount.text = messageList[position].amount
        holder.messageItemLayoutBinding.date.text = messageList[position].date
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

}
