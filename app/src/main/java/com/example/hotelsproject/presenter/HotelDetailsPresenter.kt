package com.example.hotelsproject.presenter

import com.example.hotelsproject.model.repositories.HotelRepository
import com.example.hotelsproject.view.HotelDetailsView

class HotelDetailsPresenter(
    private var view : HotelDetailsView,
    private var repository : HotelRepository) {

    fun loadHotelDetails(id : Long) {
        repository.findHotelById(id) {hotel ->
            if (hotel != null) {
                view.showHotelDetails(hotel)
            }
            else {
                view.errorHotelNotFound()
            }
        }
    }
}