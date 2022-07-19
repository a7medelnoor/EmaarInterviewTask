package com.a7medelnoor.emaarinterviewtask.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a7medelnoor.emaarinterviewtask.data.local.UserEntity
import com.a7medelnoor.emaarinterviewtask.data.models.UserResponse
import com.a7medelnoor.emaarinterviewtask.respository.UserRepository
import com.a7medelnoor.emaarinterviewtask.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
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
@Inject constructor(
    private val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application) {
    var networkStatus = false
    val TAG = "HomeViewModel"
    val context: Context? = null

    /** RETROFIT**/
    val user_Response: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun getUsers() = viewModelScope.launch {
        getUserSafeCall()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun getUserSafeCall() {
        user_Response.value = NetworkResult.LOADING()
        if (hasInternetConnection()) {
            try {
                val response = userRepository.getUserData()
                user_Response.value = handleUserResponse(response)

                val userDatas = user_Response.value!!.data
                if (userDatas != null) {
                    offlineCacheUsers(userDatas)
                }
            } catch (e: Exception) {
                user_Response.value = NetworkResult.ERROR("Recipes Not Found")
            }
        } else {
            user_Response.value = NetworkResult.ERROR("No Internet Connection")
        }
    }

    private fun handleUserResponse(response: Response<UserResponse>): NetworkResult<UserResponse>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.ERROR("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.ERROR("API Key Limited")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.ERROR("USER Not Found")
            }
            response.isSuccessful -> {
                val userDataSuccess = response.body()
                return NetworkResult.SUCCESS(userDataSuccess!!)
            }
            else -> {
                return NetworkResult.ERROR(response.message())
            }
        }
    }

    private fun offlineCacheUsers(userData: UserResponse) {
        val recipesEntity = UserEntity(userData)
        insertUsers(recipesEntity)
    }

    val readUserDataFromDataSource: LiveData<List<UserEntity>> = userRepository.local.readDataBase()
    private fun insertUsers(userEntity: UserEntity) =
        // running dispatchers because we are running database work
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.local.insertRecipes(userEntity)
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }
}