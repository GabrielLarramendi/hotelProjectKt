package com.example.hotelsproject.presenter

import com.example.hotelsproject.model.utils.HotelValidator
import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.model.repositories.HotelRepository
import com.example.hotelsproject.view.HotelFormView
import java.lang.Exception

class HotelFormPresenter(
    private val view: HotelFormView,
    private val repository: HotelRepository
) {

    private val validator =
        HotelValidator()

    fun loadHotel(id : Long) {
        repository.findHotelById(id) {hotel ->
            if (hotel != null) {
                view.showHotel(hotel)
            }
        }
    }

    fun saveHotel(hotel: Hotel) : Boolean {
        return if (validator.validate(hotel)) {
            try {
                repository.saveHotel(hotel)
                true
            } catch (e : Exception) {
                view.errorSaveHotel()
                false
            }
        } else {
            view.errorInvalidHotel()
            false
        }
    }
}