package com.a7medelnoor.emaarinterviewtask.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
/**
 ** @author Ahmed Elnoor
 ** Email: ahdnoor4@gmail.com
 ** Github: @a7medelnoor
 ** @since 18/07/2022
 ** This Assessment for Emaar Android developer interview task
 ** IT people Gulf
 ** Don't use this code for commercial propose
 **/
@Parcelize
data class Result(
    val dob: @RawValue Dob,
    val email: String,
    val id: @RawValue Id,
    val location: @RawValue Location,
    val name: @RawValue Name,
    val nat: String,
    val picture: @RawValue Picture,
    val registered: @RawValue Registered
) : Parcelable