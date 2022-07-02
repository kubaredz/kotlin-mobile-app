package pl.edu.pja.project2.home

import android.widget.ImageView

data class EventModel(
    val nameEvent: String,
    val location: String,
    val image: ImageView,
    val date: String,
    val note: String,
    val email: String
)
