package com.example.yourbank.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterfaceSendBinToServer {

    @GET("{bin}")
    fun getDataCardToBin( @Path("bin") bin : String): Call<ResponseDataYourCard>
}