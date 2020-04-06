package com.example.retrodixit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrodixit.databinding.AdapterCardBinding

class CardAdapter : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    class ViewHolder(binding: AdapterCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val vBinding = binding
    }

    private val list: ArrayList<Int> = ArrayList()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapter_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vBinding.cardId = list[position]
        holder.vBinding.root.setOnClickListener { v -> pItemListener?.let { it(list[position]) } }
    }

    fun replace(items: List<Int>?) {
        list.clear()
        items?.let { list.addAll(items) }
        notifyDataSetChanged()
    }

    lateinit var pItemListener: (Int) -> Unit
    fun setOnItemListener(l: (Int) -> Unit) {
        pItemListener = l
    }
}