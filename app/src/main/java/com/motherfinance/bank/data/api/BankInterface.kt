package com.motherfinance.bank.data.api

import com.motherfinance.bank.data.model.BankListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface BankInterface {
    @GET("api/banks")
    fun getBankList(): Single<BankListResponse>
}