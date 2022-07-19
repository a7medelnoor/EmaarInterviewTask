package com.a7medelnoor.emaarinterviewtask.respository

import com.a7medelnoor.emaarinterviewtask.data.local.LocalDataSource
import com.a7medelnoor.emaarinterviewtask.data.remote.api.ApiService
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
class UserRepository
@Inject constructor(
    private val apiService: ApiService,
    local: LocalDataSource
) {

    suspend fun getUserData() = apiService.getUserData()
    val local = local

}