package com.example.hotelsproject.view

import com.example.hotelsproject.model.entities.Hotel

interface HotelFormView {
    fun showHotel(hotel: Hotel)  //Serve apenas para mostrar o hotel existente
    fun errorInvalidHotel() // Hotel preenchido invalidamente
    fun errorSaveHotel() // Erro ao tentar salvar hotel
}