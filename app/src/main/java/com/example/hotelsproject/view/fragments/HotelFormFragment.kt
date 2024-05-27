package com.example.hotelsproject.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.hotelsproject.R
import com.example.hotelsproject.model.entities.Hotel
import com.example.hotelsproject.model.repositories.MemoryRepository
import com.example.hotelsproject.presenter.HotelFormPresenter
import com.example.hotelsproject.view.HotelFormView
import com.google.android.material.textfield.TextInputEditText

class HotelFormFragment : DialogFragment(), HotelFormView {

    private val presenter = HotelFormPresenter(this, MemoryRepository)

    private lateinit var hotelName : TextInputEditText
    private lateinit var hotelAddress : TextInputEditText
    private lateinit var hotelRating : RatingBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotelName = view.findViewById(R.id.edtName)
        hotelAddress = view.findViewById(R.id.edtAddress)
        hotelRating = view.findViewById(R.id.rtbRating)

        val hotelId = arguments?.getLong(EXTRA_HOTEL_ID, 0) ?: 0
        presenter.loadHotel(hotelId)

        hotelAddress.setOnEditorActionListener {_, i, _ ->
            handleKeyBoardEvent(i)
        }

        dialog?.setTitle(R.string.action_new_hotel)

        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )

    }

    override fun showHotel(hotel: Hotel) {
        hotelName.setText(hotel.name)
        hotelAddress.setText(hotel.address)
        hotelRating.rating = hotel.rating
    }

    override fun errorInvalidHotel() {
        Toast.makeText(requireContext(), R.string.error_invalid_hotel, Toast.LENGTH_SHORT).show()
    }

    override fun errorSaveHotel() {
        Toast.makeText(requireContext(), R.string.error_hotel_not_found, Toast.LENGTH_SHORT).show()
    }

    private fun handleKeyBoardEvent(actionId : Int) : Boolean {
        if(EditorInfo.IME_ACTION_DONE == actionId) {
            val hotel = saveHotel()
            if(hotel != null) {
                if(activity is OnHotelSavedListener) {
                    val listener = activity as OnHotelSavedListener
                    listener.onHotelSaved(hotel)
                }
                dialog?.dismiss()
                return true
            }
        }
        return false
    }

    private fun saveHotel() : Hotel? {
        val hotel = Hotel()
        val hotelId = arguments?.getLong(EXTRA_HOTEL_ID, 0) ?: 0
        hotel.id = hotelId
        hotel.name = hotelName.text.toString()
        hotel.address = hotelAddress.text.toString()
        hotel.rating = hotelRating.rating

        return if(presenter.saveHotel(hotel)) {
            hotel
        } else {
            null
        }
    }

    fun open(fm : FragmentManager) {
        if(fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "editDialog"
        private const val EXTRA_HOTEL_ID = "hotel_id"

        fun newInstance(hotelID : Long = 0) = HotelFormFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, hotelID)
            }
        }
    }

    interface OnHotelSavedListener {
        fun onHotelSaved(hotel: Hotel)
    }
}