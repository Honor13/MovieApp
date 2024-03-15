package com.example.movieapp.presentation.ui.fragments.bookingschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentBookingScheduleBinding
import com.example.movieapp.presentation.ui.viewmodels.BookingViewModel
import com.example.movieapp.presentation.ui.viewmodels.FirebaseOperationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class BookingScheduleFragment : Fragment() {

    private lateinit var binding: FragmentBookingScheduleBinding

    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var viewModel: BookingViewModel
    private lateinit var fireBaseOperationsViewModel: FirebaseOperationsViewModel
    private var date: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_booking_schedule,
            container,
            false
        )

        viewModel.loadItems()

        val bundle: BookingScheduleFragmentArgs by navArgs()
        val movieDetails = bundle.detailsResult
        binding.detailsResult = movieDetails
        binding.vote = String.format(Locale.US, "%,.1f", movieDetails.voteAverage)

        binding.apply {
            dateTimeStarDustChipGroup.setOnCheckedStateChangeListener { group, checkedId ->
                viewModel.clearOtherChipGroupSelection(group, binding.dateTimeCosmosChipGroup)
            }

            dateTimeCosmosChipGroup.setOnCheckedStateChangeListener { group, checkedId ->
                viewModel.clearOtherChipGroupSelection(group, binding.dateTimeStarDustChipGroup)
            }
        }



        //Read Spinner
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            binding.dateTimeStarDustChipGroup.clearCheck()
            binding.dateTimeCosmosChipGroup.clearCheck()
            viewModel.disableChipsBasedOnTime(binding.dateTimeStarDustChipGroup, it)
            viewModel.disableChipsBasedOnTime(binding.dateTimeCosmosChipGroup, it)
        }

        // spinner item observe
        viewModel.items.observe(viewLifecycleOwner) { items ->
            items?.let {
                dateAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = dateAdapter
            }
        }

        binding.button2.setOnClickListener {
            //Booking Process
            fireBaseOperationsViewModel.checkAndSaveBooking(movieDetails.id.toString(),"randomUser",date.toString(),viewModel.chipTime.toString())

            val action =
                BookingScheduleFragmentDirections.actionBookingScheduleFragmentToBookNowFragment(
                    movieDetails,
                    viewModel.movieTheaterName,
                    viewModel.chipTime,
                    date
                )
            Navigation.findNavController(it).navigate(action)
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onItemSelected(position)
                val selectedDateString = parent?.getItemAtPosition(position).toString()
                val selectedDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateString)


                date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate!!)
                viewModel.setSelectedDate(selectedDate)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        fireBaseOperationsViewModel = ViewModelProvider(this).get(FirebaseOperationsViewModel::class.java)
    }



}