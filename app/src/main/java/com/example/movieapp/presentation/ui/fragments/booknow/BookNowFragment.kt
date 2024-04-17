package com.example.movieapp.presentation.ui.fragments.booknow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentBookNowBinding
import com.example.movieapp.presentation.ui.viewmodels.BookingViewModel
import com.example.movieapp.presentation.ui.viewmodels.FirebaseOperationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class BookNowFragment : Fragment() {

    private lateinit var binding: FragmentBookNowBinding
    private lateinit var firebaseViewModel: FirebaseOperationsViewModel
    private lateinit var bookingViewModel: BookingViewModel
    var getIds = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_book_now, container, false)

        val bundle: BookNowFragmentArgs by navArgs()
        val movie = bundle.detailsResult
        val date = bundle.date
        val theaterName = bundle.movieThaeterName
        val time = bundle.chipTime
        binding.movie = movie
        binding.vote = String.format(Locale.US, "%,.1f", movie.voteAverage)
        binding.date = date
        binding.movieTheaterName = theaterName
        binding.time = time
        binding.bookingViewModel = bookingViewModel

        firebaseViewModel.getBooking("randomUser",movie.id.toString(),date.toString(),time.toString())

        firebaseViewModel.listBookingSeats.observe(viewLifecycleOwner){
            binding.booking = it
        }

        // Get Seat Id's
        bookingViewModel.selectedSeatIds.observe(viewLifecycleOwner){
            Log.e("Dante",it.toString())
            getIds = it
            binding.price = bookingViewModel.ticketPrice(getIds.size)
            binding.ticketCount = getIds.size.toString()
        }

        binding.button3.setOnClickListener {
            if (!getIds.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_bookNowFragment_to_selectCardBottomSheet)

            }else {
                Toast.makeText(requireContext(),"Please select seats",Toast.LENGTH_LONG).show()
            }
//            }else if (isCardSelected == true && !getIds.isNullOrEmpty()){
//                firebaseViewModel.splitIdsAndSave(getIds,movie.id.toString(),date.toString(),time.toString())
//                getIds= emptyList()
//            }

        }







        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseViewModel = ViewModelProvider(this).get(FirebaseOperationsViewModel::class.java)
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
    }

//    val imageViewList: List<ImageView> = listOf(
//        binding.A1, binding.A2, binding.A3,binding.A4, binding.A5, binding.A6,binding.A7, binding.A8, binding.A9,binding.A10, binding.A11, binding.A12,
//        binding.B1, binding.B2, binding.B3,binding.B4, binding.B5, binding.B6,binding.B7, binding.B8, binding.B9,binding.B10, binding.B11, binding.B12,
//        binding.C1, binding.C2, binding.C3,binding.C4, binding.C5, binding.C6,binding.C7, binding.C8, binding.C9,binding.C10, binding.C11, binding.C12,
//        binding.D1, binding.D2, binding.D3,binding.D4, binding.D5, binding.D6,binding.D7, binding.D8, binding.D9,binding.D10, binding.D11, binding.D12,
//        binding.E1, binding.E2, binding.E3,binding.E4, binding.E5, binding.E6,binding.E7, binding.E8, binding.E9,binding.E10, binding.E11, binding.E12,
//        binding.F1, binding.F2, binding.F3,binding.F4, binding.F5, binding.F6,binding.F7, binding.F8, binding.F9,binding.F10, binding.F11, binding.F12
//    )


}