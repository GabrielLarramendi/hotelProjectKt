package com.example.hotelsproject.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotelsproject.R
import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.model.repositories.MemoryRepository
import com.example.hotelsproject.presenter.HotelDetailsPresenter
import com.example.hotelsproject.view.HotelDetailsView

class HotelDetailsFragment : Fragment(), HotelDetailsView {

    private val presenter = HotelDetailsPresenter(this, MemoryRepository)
    private var hotel : Hotel? = null

    private lateinit var hotelName : TextView
    private lateinit var hotelAddress : TextView
    private lateinit var hotelRatingBar : RatingBar

    override fun onCreateView( //View sendo criada
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //View ja criada
        super.onViewCreated(view, savedInstanceState)

        hotelName = view.findViewById(R.id.textHotelNameFragment)
        hotelAddress = view.findViewById(R.id.textHotelAddressFragment)
        hotelRatingBar = view.findViewById(R.id.ratingBarHotelDetailsFragment)

        presenter.loadHotelDetails(arguments?.getLong(EXTRA_HOTEL_ID, -1) ?: -1)
    }

    override fun showHotelDetails(hotel: Hotel) {
        this.hotel = hotel
        hotelName.text = hotel.name
        hotelAddress.text = hotel.address
        hotelRatingBar.rating = hotel.rating
    }

    override fun errorHotelNotFound() {
        hotelName.text = R.string.error_hotel_not_found.toString()
        hotelAddress.visibility = View.GONE
        hotelRatingBar.visibility = View.GONE
    }

    companion object {
        const val TAG_DETAILS = "tagDetails"
        const val EXTRA_HOTEL_ID = "hotelId"

        fun newInstance(id : Long) = HotelDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, id)
            }
        }
    }
}