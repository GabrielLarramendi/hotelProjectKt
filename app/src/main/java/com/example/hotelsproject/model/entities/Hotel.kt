package com.example.hotelsproject.model.entities

data class Hotel(
    var id : Long = 0,
    var name : String = "",
    var address : String = "",
    var rating : Float = 0.0F,
    var isSelected : Boolean = false
) {
    override fun toString(): String = name
}
