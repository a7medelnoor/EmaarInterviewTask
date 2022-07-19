package com.a7medelnoor.emaarinterviewtask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class],
        version = 1,
exportSchema = false)
@TypeConverters(UserDataTypeConverter::class)
abstract  class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO
}