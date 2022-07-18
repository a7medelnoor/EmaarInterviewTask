package com.a7medelnoor.emaarinterviewtask.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a7medelnoor.emaarinterviewtask.data.models.UserResponse
import com.a7medelnoor.emaarinterviewtask.respository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 ** @author Ahmed Elnoor
 ** Email: ahdnoor4@gmail.com
 ** Github: @a7medelnoor
 ** @since 18/07/2022
 ** This Assessment for Emaar Android developer interview task
 ** IT people Gulf
 ** Don't use this code for commercial propose
 **/
@HiltViewModel
class HomeViewModel
@Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _response = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse>
        get() = _response

    init {
        getUserData()
    }

    private fun getUserData() = viewModelScope.launch {
        userRepository.getUserData().let { response ->
            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Log.d("response error", "getRecipe: ${response.code()}")
            }

        }
    }


}