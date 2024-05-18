package com.example.hotelsproject.view

import com.example.hotelsproject.model.entities.Hotel

interface HotelListView {
    fun showHotelsList(hotels : List<Hotel>)
    fun showHotelDetails(hotel: Hotel)
}