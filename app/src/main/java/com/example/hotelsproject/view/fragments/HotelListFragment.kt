package com.example.hotelsproject.view.fragments

import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size

import androidx.fragment.app.ListFragment
import com.example.hotelsproject.R
import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.model.repositories.MemoryRepository
import com.example.hotelsproject.presenter.HotelListPresenter
import com.example.hotelsproject.view.HotelListView

class HotelListFragment :
    ListFragment(),
    HotelListView,
    AdapterView.OnItemLongClickListener,
    ActionMode.Callback {

    private val presenter = HotelListPresenter(this, MemoryRepository)
    private var actionMode : ActionMode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        presenter.init()
        listView.onItemLongClickListener = this
    }


    override fun showHotelsList(hotels: List<Hotel>) {
        val adapter = ArrayAdapter<Hotel>(requireContext(), android.R.layout.simple_list_item_1, hotels)
        listAdapter = adapter
    }

    override fun showHotelDetails(hotel: Hotel) {
        if (activity is OnHotelClickListener) {
            val listener = activity as OnHotelClickListener
            listener.onHotelClick(hotel)
        }
    }

    override fun showDeleteMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startActionMode(this)
        listView.onItemLongClickListener = null
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    override fun hideDeleteMode() {
        listView.onItemLongClickListener = this
        for(i in 0 until listView.count) {
            listView.setItemChecked(i, false)
        }
        listView.post {
            actionMode?.finish()
            listView.choiceMode = ListView.CHOICE_MODE_NONE
        }
    }

    override fun showSelectedHotels(hotels: List<Hotel>) {
        listView.post {
            for(i in 0 until listView.count) {
                val hotel = listView.getItemAtPosition(i) as Hotel
                hotel.isSelected = false
                if (hotels.find { it.id == hotel.id} != null) {
                    listView.setItemChecked(i, true)
                    hotel.isSelected = true
                }
            }
        }
    }

    override fun updateSelectedItemsCountForContextualActionBar(count: Int) {
        view?.post {
            actionMode?.title = resources.getQuantityString(R.plurals.list_hotel_selected, count, count)
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val hotel = l.getItemAtPosition(position) as Hotel
        presenter.selectHotel(hotel)
    }

    fun search(text : String) {
        presenter.searchHotels(text)
    }

    fun clearSearch() {
        presenter.searchHotels("")
    }

    interface OnHotelClickListener {
        fun onHotelClick(hotel: Hotel)
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        val consumed = (actionMode == null)
        if(consumed) {
            val hotel = parent?.getItemAtPosition(position) as Hotel
            presenter.showDeleteMode()
            presenter.selectHotel(hotel)
        }
        return consumed
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        activity?.menuInflater?.inflate(R.menu.hotel_delete_list, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_delete) {
            presenter.deleteSelected { hotels ->
                if (activity is OnHotelDeletedListener) {
                    (activity as OnHotelDeletedListener).onHotelsDeleted(hotels)
                }
            }
            return true
        }
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        presenter.hideDeleteMode()
    }

    interface OnHotelDeletedListener {
        fun onHotelsDeleted(hotels: List<Hotel>)
    }

}