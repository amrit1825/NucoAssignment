package com.amrit.bravo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.amrit.bravo.model.Message
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.time.format.DateTimeFormatter

const val TAG = "Receiver"
const val PDU_TYPE = "pdus"

class MessageReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val bundle = intent.extras
        val smsList: Array<SmsMessage?>
        var messageBody: String
        bundle?.let { it1 ->
            val format = it1.getString("format")
            val pdu = it1.get(PDU_TYPE) as Array<Any>?
            pdu?.let { it2 ->
                smsList = arrayOfNulls(it2.size)
                for (i in smsList.indices) {
                    smsList[i] = SmsMessage.createFromPdu(it2[i] as ByteArray, format)
                    smsList[i]?.let {
                        messageBody = it.messageBody
                        val messageValidator = MessageValidator()
                        if (messageValidator.hasDate(messageBody) && messageValidator.hasAmount(messageBody)){
                            writeDataToServer(Message(messageValidator.getAmount(), Timestamp(System.currentTimeMillis()).toString()))
                        }
                    }
                }
            }
        }
    }

    private fun writeDataToServer(message: Message) {
        val myRef = Firebase.database.getReference("user1")
        myRef.push().setValue(message).addOnSuccessListener {
            Log.d(TAG, "Success")
        }.addOnFailureListener {
            Log.d(TAG, "Failure")
        }
    }

}
