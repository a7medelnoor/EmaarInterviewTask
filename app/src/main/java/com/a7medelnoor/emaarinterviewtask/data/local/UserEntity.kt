package com.a7medelnoor.emaarinterviewtask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a7medelnoor.emaarinterviewtask.data.models.Result
import com.a7medelnoor.emaarinterviewtask.data.models.UserResponse
import com.a7medelnoor.emaarinterviewtask.util.Constants


@Entity(tableName = Constants.USER_TABLE)
class UserEntity(
    var userData: UserResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}