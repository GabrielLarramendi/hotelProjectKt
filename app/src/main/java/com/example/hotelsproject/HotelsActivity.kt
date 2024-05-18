package com.example.hotelsproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.view.fragments.HotelDetailsFragment
import com.example.hotelsproject.view.fragments.HotelListFragment

class HotelsActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onHotelClick(hotel: Hotel) {
        if(isTablet()) {
            showDetailsFragment(hotel.id)
        }
        if (isSmartphone()) {
            showDetailsActivity(hotel.id)
        }
    }

    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsFragment(hotelId : Long) {
        val fragment = HotelDetailsFragment.newInstance(hotelId)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
            .commit()
    }

    private fun showDetailsActivity(hotelId : Long) {
        HotelDetailsActivity.open(this, hotelId)
    }
}