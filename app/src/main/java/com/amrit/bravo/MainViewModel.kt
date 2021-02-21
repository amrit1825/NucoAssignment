package com.amrit.bravo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amrit.bravo.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    private val messageList = MutableLiveData<List<Message>>()

    fun getDataFromServer() {
        val myRef: DatabaseReference = Firebase.database.getReference("user1")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<Message>()
                for (postSnapshot in snapshot.children) {
                    val data: Message? = postSnapshot.getValue(Message::class.java)
                    data?.let {
                        dataList.add(it)
                    }
                }
                messageList.value = dataList.asReversed()
            }

            override fun onCancelled(error: DatabaseError) {
                // do nothing
            }
        })
    }

    fun getMessageList(): MutableLiveData<List<Message>> {
        return messageList
    }

}