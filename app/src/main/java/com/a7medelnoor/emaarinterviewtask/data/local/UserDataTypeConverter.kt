package com.a7medelnoor.emaarinterviewtask.data.local

import androidx.room.TypeConverter
import com.a7medelnoor.emaarinterviewtask.data.models.UserResponse
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class UserDataTypeConverter {
    var gson = Gson()

    // convert user object to string
    @TypeConverter
    fun userDataToString(userResponse: UserResponse): String {
        return gson.toJson(userResponse)
    }

    // convert string to user object
    @TypeConverter
    fun stringToUserData(data: String): UserResponse {
        val listType = object : TypeToken<UserResponse>() {}.type
        return gson.fromJson(data, listType)
    }

//    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

}