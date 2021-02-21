package com.amrit.bravo

import java.util.regex.Pattern

class MessageValidator : MessageMatcher {

    private val datePattern: Pattern =
        Pattern.compile("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-\\d{2}" + "|^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-\\d{4}")

    private var position: Int = 0
    private var amount = ""

    private val amountPattern = "INR."

    override fun hasDate(date: String): Boolean {
        val array = date.split(" ").toTypedArray()
        for (x in array) {
            if (datePattern.matcher(x).matches())
                return true
        }
        return false
    }

    override fun hasAmount(date: String): Boolean {
        val array = date.split(" ").toTypedArray()
        for (x in array.indices) {
            if (amountPattern == array[x]) {
                position = 0 + 1
                amount = array[position]
                return true
            }
        }
        return false
    }

    fun getAmount(): String {
        return amount
    }

}