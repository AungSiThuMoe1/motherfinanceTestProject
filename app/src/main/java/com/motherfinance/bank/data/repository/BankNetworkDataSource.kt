package com.motherfinance.bank.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.motherfinance.bank.data.api.BankInterface
import com.motherfinance.bank.data.model.BankListResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import java.lang.Exception

class BankNetworkDataSource(private val apiService : BankInterface , private val compositeDisposable: CompositeDisposable){
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
    get() = _networkState
    private val _downloadBankResponse = MutableLiveData<BankListResponse>()
    val downloadedBankResponse : LiveData<BankListResponse>
    get() = _downloadBankResponse

    fun fetchBankList(){
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getBankList()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadBankResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("BankDataSource", it.message.toString())
                        }
                    )
            )
        }catch (e : Exception)
        {
            Log.e("BankDataSource", e.message.toString())
        }
    }

}