package com.motherfinance.bank.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.motherfinance.bank.data.api.BankInterface
import com.motherfinance.bank.data.model.BankListResponse
import io.reactivex.disposables.CompositeDisposable

class BankDataSource(private val apiService : BankInterface) {
    lateinit var bankListNetworkDataSource: BankNetworkDataSource
    fun fetchSingleNewPostList(compositeDisposable: CompositeDisposable) : LiveData<BankListResponse>{
        bankListNetworkDataSource = BankNetworkDataSource(apiService,compositeDisposable)
        bankListNetworkDataSource.fetchBankList()
        return bankListNetworkDataSource.downloadedBankResponse
    }

    fun getBankListNetworkState() : LiveData<NetworkState>
    {
        return bankListNetworkDataSource.networkState
    }

}