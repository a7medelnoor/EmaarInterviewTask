package com.a7medelnoor.emaarinterviewtask.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.a7medelnoor.emaarinterviewtask.data.models.Result
import com.a7medelnoor.emaarinterviewtask.data.models.UserResponse
import com.a7medelnoor.emaarinterviewtask.databinding.UserLayoutAdapterBinding
import com.a7medelnoor.emaarinterviewtask.ui.fragments.home.HomeFragmentDirections
import com.a7medelnoor.emaarinterviewtask.util.UserDiffUtill
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

class UserRecyclerViewAdapter : RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {
    val TAG = "UserRecyclerViewAdapter"

    inner class UserViewHolder(val binding: UserLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack =
        object : DiffUtil.ItemCallback<com.a7medelnoor.emaarinterviewtask.data.models.Result>() {
            override fun areItemsTheSame(oldITem: Result, newItem: Result): Boolean {
                return oldITem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }

    private val differ = AsyncListDiffer(this, differCallBack)
    var userData: List<Result>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserLayoutAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: UserViewHolder, position: Int) {
        var currentUser = userData[position]
        viewHolder.binding.apply {
            userFirstName.text = currentUser.name.first
            userLastName.text = currentUser.name.last
            userEmail.text = currentUser.email
            userCountry.text = currentUser.location.country
            val date = currentUser.registered.date
            val offsetTime: OffsetDateTime = OffsetDateTime.parse(date)
            // extract date
            val dateExtracted: LocalDate = offsetTime.toLocalDate()
            // format the date
            val dateFormatted = dateExtracted.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
            userRegistrationDate.text = dateFormatted

            userThumbnialImageView.load(currentUser.picture.large) {
                crossfade(true)
                crossfade(1000)
            }
        }
        viewHolder.itemView.setOnClickListener { mView ->
           val direction = HomeFragmentDirections
               .actionHomeFragmentToUserDetailsFragment(currentUser)
            mView.findNavController().navigate(direction)

        }
    }

    override fun getItemCount() = userData.size
    fun setData(newData: UserResponse) {
        val recipesDiffUtil = UserDiffUtill(userData, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        userData = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}