package com.amrit.bravo

import java.util.regex.Pattern

class MessageValidator : MessageMatcher {

    // date pattern considering day from 01 to 31, month from 01 to 12, and year can be entered as like 21 or 2021
    // date format will be dd-mm-yy and dd-mm-yyyy like "21-02-21", "21-02-2021"
    private val datePattern: Pattern =
        Pattern.compile("^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-\\d{2}" + "|^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-\\d{4}")

    private var position: Int = 0
    private var amount = ""

    // amount will be compared with INR. mention in message like "INR. 25"
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
                position = x + 1
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