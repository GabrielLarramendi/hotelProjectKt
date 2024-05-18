package com.example.hotelsproject.model.repositories

import com.example.hotelsproject.model.entities.Hotel

object MemoryRepository : HotelRepository {

    private var nextId = 1L
    private val hotelList = mutableListOf<Hotel>()

    init {
        saveHotel(Hotel(0, "Ibis Budget", "Pajuçara", 3.5F))
        saveHotel(Hotel(0, "Atlantic Suites", "Jatiúca", 4.0F))
        saveHotel(Hotel(0, "Costamar", "Ponta Verde", 3.0F))
        saveHotel(Hotel(0, "Ritz", "Cruz das Almas", 5F))
        saveHotel(Hotel(0, "Brisa Tower", "Jatiúca", 4F))
        saveHotel(Hotel(0, "Maceió Mar Hotel", "Ponta Verde", 4F))
        saveHotel(Hotel(0, "Meridiano", "Pajuçara", 5F))
        saveHotel(Hotel(0, "Ponta Verde", "Ponta Verde", 4.5F))
        saveHotel(Hotel(0, "Ponta Verde", "Francês", 5F))
    }

    override fun saveHotel(hotel: Hotel) {
        if (hotel.id == 0L) {
            hotel.id = nextId ++
            hotelList.add(hotel)
        }
        else {
            val index = hotelList.indexOfFirst { it.id == hotel.id }
            if (index != -1) {
                hotelList[index] = hotel
            }
            else {
                hotelList.add(hotel)
            }
        }
    }

    override fun removeHotel(vararg hotels: Hotel) {
        hotelList.removeAll(hotels.toSet())
    }

    override fun findHotelById(id: Long, callback: (Hotel?) -> Unit) {
        callback(hotelList.find { it.id == id })
    }

    override fun searchHotels(term: String, callback: (List<Hotel>) -> Unit) {
        callback(
            if (term.isEmpty()) hotelList
            else hotelList.filter {
                it.name.uppercase().contains(term.uppercase())
            }
        )
    }
}