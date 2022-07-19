package com.a7medelnoor.emaarinterviewtask.data.local

import androidx.lifecycle.LiveData
import com.a7medelnoor.emaarinterviewtask.respository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDAO: UserDAO
) {
    fun readDataBase(): LiveData<List<UserEntity>> {
        return userDAO.readUserData()
    }
    // function for inserting the recipes into the database
    suspend fun insertRecipes(userEntity: UserEntity){
        userDAO.insertUserData(userEntity )
    }
}