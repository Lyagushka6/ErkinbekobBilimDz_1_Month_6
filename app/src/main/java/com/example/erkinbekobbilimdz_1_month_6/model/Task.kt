package com.example.erkinbekobbilimdz_1_month_6.model

data class Task(
    val title: String,
    var isComplete: Boolean = false,
) {

    fun toggleDone() {
        isComplete = !isComplete
    }
}
