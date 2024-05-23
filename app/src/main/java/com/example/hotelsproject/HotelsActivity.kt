package com.example.hotelsproject

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
//import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.view.fragments.HotelDetailsFragment
import com.example.hotelsproject.view.fragments.HotelListFragment

class HotelsActivity : AppCompatActivity(),
    HotelListFragment.OnHotelClickListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener
{

    private var lastTermSearch : String = ""
    private var searchView : androidx.appcompat.widget.SearchView? = null
    private val listFragment : HotelListFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragmentListHotels) as HotelListFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(EXTRA_SEARCH_TERM, lastTermSearch)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        lastTermSearch = savedInstanceState?.getString(EXTRA_SEARCH_TERM) ?: ""
    }

    override fun onHotelClick(hotel: Hotel) {
        if(isTablet()) {
            showDetailsFragment(hotel.id)
        }
        if (isSmartphone()) {
            showDetailsActivity(hotel.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if (lastTermSearch.isNotEmpty()) {
            Handler().post {
                val query = lastTermSearch
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsFragment(hotelId : Long) {
        val fragment = HotelDetailsFragment.newInstance(hotelId)

        searchView?.setOnQueryTextListener(null)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
            .commit()
    }

    private fun showDetailsActivity(hotelId : Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastTermSearch = newText ?: ""
        listFragment.search(lastTermSearch)
        return true
    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean = true


    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        lastTermSearch = ""
        listFragment.clearSearch()
        return true
    }

    companion object {
        const val EXTRA_SEARCH_TERM = "lastSearch"
    }
}