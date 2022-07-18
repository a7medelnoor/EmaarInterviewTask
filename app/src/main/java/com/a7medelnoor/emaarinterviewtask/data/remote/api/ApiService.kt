package com.a7medelnoor.emaarinterviewtask.data.remote.api

import com.a7medelnoor.emaarinterviewtask.data.models.UserResponse
import com.a7medelnoor.emaarinterviewtask.util.Constants
import retrofit2.Response
import retrofit2.http.GET
/**
 ** @author Ahmed Elnoor
 ** Email: ahdnoor4@gmail.com
 ** Github: @a7medelnoor
 ** @since 18/07/2022
 ** This Assessment for Emaar Android developer interview task
 ** IT people Gulf
 ** Don't use this code for commercial propose
 **/
interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getUserData(): Response<UserResponse>
}