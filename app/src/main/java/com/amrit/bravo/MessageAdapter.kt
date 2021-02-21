package com.amrit.bravo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amrit.bravo.databinding.MessageItemLayoutBinding
import com.amrit.bravo.model.Message

class MessageAdapter(private var messageList: List<Message>) :
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
        // setting amount and date for every item in UI
        holder.messageItemLayoutBinding.amount.text = messageList[position].amount
        holder.messageItemLayoutBinding.date.text = messageList[position].date
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun updateMessageList(messageList: List<Message>) {
        // method for updating adapter data when ever there is update on server side
        this.messageList = messageList
    }

}
