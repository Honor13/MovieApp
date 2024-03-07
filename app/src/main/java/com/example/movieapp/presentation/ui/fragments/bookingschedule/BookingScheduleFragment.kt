package com.example.movieapp.presentation.ui.fragments.bookingschedule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentBookingScheduleBinding
import com.example.movieapp.presentation.ui.viewmodels.BookingViewModel

class BookingScheduleFragment : Fragment() {

    private lateinit var binding: FragmentBookingScheduleBinding
    private val dateArray = ArrayList<String>()
    private lateinit var dateAdapter:ArrayAdapter<String>
    private lateinit var viewModel: BookingViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_booking_schedule,container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.loadItems()

        // spinner item observe
        viewModel.items.observe(viewLifecycleOwner) { items ->
            items?.let {
                Log.e("Dante","frg")
                dateAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = dateAdapter
            }
        }



        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onItemSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Herhangi bir şey seçilmediğinde yapılacak işlemler
            }
        }



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
    }



}