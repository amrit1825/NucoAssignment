package com.amrit.bravo

interface MessageMatcher {

    fun hasDate(date: String): Boolean
    fun hasAmount(date: String): Boolean
}