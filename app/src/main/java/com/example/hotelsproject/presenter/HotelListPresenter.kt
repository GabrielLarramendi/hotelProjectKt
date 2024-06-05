package com.example.hotelsproject.presenter

import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.model.repositories.HotelRepository
import com.example.hotelsproject.view.HotelListView

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {

    private var lastTerm = ""
    private var inDeleteMode = false
    private val selectedItems = mutableListOf<Hotel>()

    fun searchHotels(term : String) {
        lastTerm = term
        repository.searchHotels(lastTerm) {hotels ->
            view.showHotelsList(hotels)
        }
    }

    fun showHotelDetails(hotel: Hotel) {
        view.showHotelDetails(hotel)
    }

    fun selectHotel(hotel: Hotel) {
        if(inDeleteMode) {
            toggleHotelSelected(hotel)

            if(selectedItems.size == 0) {
                view.hideDeleteMode()
            } else {
                view.updateSelectedItemsCountForContextualActionBar(selectedItems.size)
                view.showSelectedHotels(selectedItems)
            }
        } else {
            view.showHotelDetails(hotel)
        }
    }

    private fun toggleHotelSelected(hotel: Hotel) { //
        val existing = selectedItems.find { it.id == hotel.id }
        if(existing == null) {
            selectedItems.add(hotel)
        }
        else selectedItems.removeAll {it.id == hotel.id}
    }


    fun showDeleteMode() {
        inDeleteMode = true
        view.showDeleteMode()
    }

    fun hideDeleteMode() {
        inDeleteMode = false
        selectedItems.clear()
        view.hideDeleteMode()
    }

    private fun refresh() {
        searchHotels(lastTerm)
    }

    fun deleteSelected(callback : (List<Hotel>) -> Unit) {
        repository.removeHotel(*selectedItems.toTypedArray())
        refresh()
        callback(selectedItems)
        hideDeleteMode()
    }

    fun init() {
        if(inDeleteMode) {
            showDeleteMode()
            view.updateSelectedItemsCountForContextualActionBar(selectedItems.size)
            view.showSelectedHotels(selectedItems)
        } else {
            refresh()
        }
    }
}