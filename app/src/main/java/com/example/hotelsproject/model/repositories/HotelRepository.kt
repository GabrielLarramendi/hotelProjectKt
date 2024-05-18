package com.example.hotelsproject.model.repositories

import com.example.hotelsproject.model.entities.Hotel

interface HotelRepository {
    fun saveHotel(hotel: Hotel)
    fun removeHotel(vararg hotels : Hotel) // Treat the parameters as an array
    fun findHotelById(id : Long, callback : (Hotel?) -> Unit) //Same as Void in Java
    fun searchHotels(term : String, callback : (List<Hotel>) -> Unit) //Same as Void in Java
}