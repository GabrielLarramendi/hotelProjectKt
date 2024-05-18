package com.example.hotelsproject.view.fragments

import com.example.hotelsproject.model.entities.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}