package com.a7medelnoor.emaarinterviewtask.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userEntity: UserEntity)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readUserData(): LiveData<List<UserEntity>>
}