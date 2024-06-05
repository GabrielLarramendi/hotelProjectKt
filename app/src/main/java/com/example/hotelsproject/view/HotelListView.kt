package com.example.hotelsproject.view

import com.example.hotelsproject.model.entities.Hotel

interface HotelListView {
    fun showHotelsList(hotels : List<Hotel>) //Responsável por mostrar a lista de hotéis
    fun showHotelDetails(hotel: Hotel)  //Responsável por tratar eventos de click para mostrar detalhes
    fun showDeleteMode() //Habilita o modo de exclusão
    fun hideDeleteMode() //Desabilida o modo de exclusão
    fun showSelectedHotels(hotels : List<Hotel>) //marca os hotéis selecionados para a exclusão
    fun updateSelectedItemsCountForContextualActionBar(count : Int) //Atualiza a quant. de hotéis selecionados
}