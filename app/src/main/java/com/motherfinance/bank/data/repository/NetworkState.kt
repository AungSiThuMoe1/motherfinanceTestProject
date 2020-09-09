package com.motherfinance.bank.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status : Status, val msg : String) {
    companion object{
        val LOADED : NetworkState
        val LOADING : NetworkState
        var ERROR : NetworkState
        var ENDOFLIST : NetworkState
        init {
            LOADED = NetworkState(Status.SUCCESS , "Success")
            LOADING = NetworkState(Status.RUNNING , "Running")
            ERROR = NetworkState(Status.FAILED , "Something went wrong.\nPlease check internet connection!")
            ENDOFLIST = NetworkState(Status.FAILED,"You have reached the end.")
        }
    }
}