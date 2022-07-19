package com.a7medelnoor.emaarinterviewtask.ui.fragments.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.a7medelnoor.emaarinterviewtask.R
import com.a7medelnoor.emaarinterviewtask.data.models.Result
import com.a7medelnoor.emaarinterviewtask.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 ** @author Ahmed Elnoor
 ** Email: ahdnoor4@gmail.com
 ** Github: @a7medelnoor
 ** @since 18/07/2022
 ** This Assessment for Emaar Android developer interview task
 ** IT people Gulf
 ** Don't use this code for commercial propose
 **/
@AndroidEntryPoint
class UserDetailsFragment : Fragment(R.layout.fragment_user_details) {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: UserDetailsFragmentArgs by navArgs()
    private lateinit var userData: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserDetailsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userData = args.result
        populateUserDetailsUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateUserDetailsUI() {
        binding.apply {
            userFirstNameDetails.text = userData.name.first
            userLastNameDetails.text = userData.name.last
            userDetailsImageView.load(userData.picture.large) {
                crossfade(true)
                crossfade(1000)
            }
            emailTextViewDetail.text = userData.email


            val dateJoined = userData.registered.date
            val offsetTime: OffsetDateTime = OffsetDateTime.parse(dateJoined)
            // extract date
            val dateExtracted: LocalDate = offsetTime.toLocalDate()
            // format the date
            val dateFormatted = dateExtracted.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
            dateJoinedDetails.text = dateFormatted

            val dateOfBirthData = userData.dob.date
            val dateOfBirthOffsetTime: OffsetDateTime = OffsetDateTime.parse(dateOfBirthData)
            // extract date of birth
            val dateOfBirthExtracted: LocalDate = dateOfBirthOffsetTime.toLocalDate()
            // format the date of birth
            val dateFormatedDOB =
                dateOfBirthExtracted.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            dateOfBirth.text = dateFormatedDOB
            val cityUserData = userData.location.city
            cityDetails.text = "\"${cityUserData}\","
            val stateUserData = userData.location.state
            stateDetails.text = "\"${stateUserData}\","
            val countryUserData = userData.location.country
            countryDetails.text = "\"${countryUserData}\","
            val postCodeUserData = userData.location.postcode
            postCodeDetails.text = "\"${postCodeUserData}\","
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar_user_details))
        binding.toolbarUserDetails.setNavigationIcon(R.drawable.ic_arrow_back)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}