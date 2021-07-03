package com.nayottama.sobatsepatu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nayottama.sobatsepatu.R
import com.nayottama.sobatsepatu.model.Order

class ListOrderAdapter(private val listOrder : List<Order>) : RecyclerView.Adapter<ListOrderAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMerk: TextView = itemView.findViewById(R.id.txtMerk)
        var tvCatatan: TextView = itemView.findViewById(R.id.txtCatatan)
        var tvJenis: TextView = itemView.findViewById(R.id.txtJenis)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_order, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val order = listOrder[position]

        holder.tvMerk.text = order.merk
        holder.tvCatatan.text = order.catatan
        holder.tvJenis.text = order.jenis


        holder.itemView.setOnClickListener() {
            onItemClickCallback
                ?.onItemClick(listOrder[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Order)
    }
}