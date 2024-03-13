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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentBookingScheduleBinding
import com.example.movieapp.presentation.ui.viewmodels.BookingViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.util.Locale

class BookingScheduleFragment : Fragment() {

    private lateinit var binding: FragmentBookingScheduleBinding

    private lateinit var dateAdapter: ArrayAdapter<String>
    private lateinit var viewModel: BookingViewModel
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
        binding.detailsResult = bundle.detailsResult
        binding.vote = String.format(Locale.US, "%,.1f", bundle.detailsResult.voteAverage)




        binding.apply {
            dateTimeStarDustChipGroup. setOnCheckedStateChangeListener { group, checkedId ->
                clearOtherChipGroupSelection(group, binding.dateTimeCosmosChipGroup)
            }

            dateTimeCosmosChipGroup. setOnCheckedStateChangeListener { group, checkedId ->
                clearOtherChipGroupSelection(group, binding.dateTimeStarDustChipGroup)
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
                Log.e("Dante", "frg")
                dateAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = dateAdapter
            }
        }

        binding.button2.setOnClickListener {
            val action =
                BookingScheduleFragmentDirections.actionBookingScheduleFragmentToBookNowFragment(
                    bundle.detailsResult
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

                // ViewModel içindeki LiveData'ye seçilen tarihi ata
                viewModel.setSelectedDate(selectedDate!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
    }


    private fun clearOtherChipGroupSelection(
        selectedChipGroup: ChipGroup,
        otherChipGroup: ChipGroup
    ) {
        for (i in 0 until selectedChipGroup.childCount) {
            val chip: Chip = selectedChipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                otherChipGroup.clearCheck()
                return
            }
        }
    }


}