package com.motherfinance.bank.ui.bank_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.motherfinance.bank.data.model.BankListResponse
import com.motherfinance.bank.data.repository.BankDataSource
import com.motherfinance.bank.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel (private val bankDataSource : BankDataSource) : ViewModel(){
    private val compositeDisposable = CompositeDisposable()
    val bankList : LiveData<BankListResponse> by lazy {
        bankDataSource.fetchSingleNewPostList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        bankDataSource.getBankListNetworkState()
    }

    fun listIsEmpty() : Boolean{
        return bankList.value?.data?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}