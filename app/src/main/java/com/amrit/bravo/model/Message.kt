package com.amrit.bravo.model

data class Message(val amount: String, val date: String) {

    // No argument constructor needed for firebase.
    constructor() : this("", "")

}