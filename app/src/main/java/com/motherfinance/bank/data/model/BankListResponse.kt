package com.motherfinance.bank.data.model

data class BankListResponse(
    val code: Int,
    val data: List<Data>,
    val message: String
)