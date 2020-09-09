package com.motherfinance.bank.ui.bank_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.motherfinance.bank.R
import com.motherfinance.bank.data.model.Data
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_bank_detail.*
import kotlinx.android.synthetic.main.bank_list_item.view.*

class BankDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Bank Details"
        setContentView(R.layout.activity_bank_detail)
        val data = Hawk.get<Data>("onebankdata");
        Glide.with(this)
            .load(data?.logoUrl)
            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
            .into(img_bank_logo)
        tv_bank_name.text = "Name : ${data.name}"
        tv_bank_type.text = "Type : ${data.type}"
    }
}