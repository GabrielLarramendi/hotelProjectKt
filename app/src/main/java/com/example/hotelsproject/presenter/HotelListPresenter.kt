package com.example.hotelsproject.presenter

import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.model.repositories.HotelRepository
import com.example.hotelsproject.view.HotelListView

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {
    fun searchHotels(term : String) {
        repository.searchHotels(term) {hotels -> view.showHotelsList(hotels)}
    }

    fun showHotelDetails(hotel: Hotel) {
        view.showHotelDetails(hotel)
    }
}