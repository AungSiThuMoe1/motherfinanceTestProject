package com.motherfinance.bank.ui.bank_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.motherfinance.bank.R
import com.motherfinance.bank.data.api.BankApiClient
import com.motherfinance.bank.data.api.BankInterface
import com.motherfinance.bank.data.model.BankListResponse
import com.motherfinance.bank.data.repository.BankDataSource
import com.motherfinance.bank.data.repository.NetworkState
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var bankDataSource: BankDataSource
    lateinit var bankListAdapter: BankListAdapter
    lateinit var apiService: BankInterface
    lateinit var bankLisResponse: BankListResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Bank Lists"
        apiService = BankApiClient.getClient()
        bankDataSource = BankDataSource(apiService)
        viewModel = getViewModel()



        bankListAdapter = BankListAdapter(this);
        val linearLayoutManager = LinearLayoutManager(this);
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_bank_list.layoutManager = linearLayoutManager
        rv_bank_list.setHasFixedSize(true)
        rv_bank_list.adapter = bankListAdapter

        viewModel.bankList.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it== NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
            txt_error.text = NetworkState.ERROR.msg
            if(!viewModel.listIsEmpty())
            {
             bankListAdapter.setNetworkState(it)
            }
        })
    }

    fun bindUI(it : BankListResponse)
    {
        print(it.data.get(0).name)
       bankListAdapter.addBankList(it.data)
    }

    private fun getViewModel() : MainActivityViewModel {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(
                    bankDataSource
                ) as T
            }
        })[MainActivityViewModel::class.java]
    }
}