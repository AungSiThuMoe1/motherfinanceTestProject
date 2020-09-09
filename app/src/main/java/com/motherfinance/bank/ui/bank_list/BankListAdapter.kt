package com.motherfinance.bank.ui.bank_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.motherfinance.bank.R
import com.motherfinance.bank.data.model.Data
import com.motherfinance.bank.data.repository.NetworkState
import com.motherfinance.bank.ui.bank_detail.BankDetailActivity
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.bank_list_item.view.*

class BankListAdapter (public val context : Context?) : RecyclerView.Adapter<BankListAdapter.BankViewHolder>(){
    private var networkState : NetworkState? = null
    private var bankList : List<Data> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BankListAdapter.BankViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view : View
        view = layoutInflater.inflate(R.layout.bank_list_item,parent,false)
        return BankViewHolder(view)
    }

    class BankViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bankItem : Data?,context: Context?){
            itemView.tv_bank_name.text = bankItem?.name
            Glide.with(itemView.context)
                .load(bankItem?.logoUrl)
                .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(itemView.img_bank_logo_thumb)

            itemView.setOnClickListener {
                val intent = Intent(context, BankDetailActivity::class.java)
                Hawk.put("onebankdata",bankItem)
                context?.startActivity(intent)

            }
        }

    }

    override fun getItemCount(): Int {
        return bankList.size
    }

    override fun onBindViewHolder(holder: BankListAdapter.BankViewHolder, position: Int) {
        (holder as BankViewHolder).bind(bankList.get(position),context)
    }


    private fun hasExtraRow() : Boolean{
        return networkState != null && networkState != NetworkState.LOADED
    }

    public fun addBankList(bankList : List<Data>)
    {
        this.bankList = bankList
        notifyDataSetChanged()
    }

    fun setNetworkState(newNetworkState : NetworkState){
        val previousState : NetworkState? = this.networkState
        val hadExtroRow : Boolean = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow : Boolean = hasExtraRow()
        if(hadExtroRow != hasExtraRow){
            if(hadExtroRow)
            {
                notifyItemRemoved(bankList.size)
            }
            else
            {
                notifyItemInserted(bankList.size)
            }
        }else if(hasExtraRow && previousState != newNetworkState)
        {

            notifyItemChanged(itemCount-1)
        }
    }

}