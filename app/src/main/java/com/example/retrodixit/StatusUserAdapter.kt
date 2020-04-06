package com.example.retrodixit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrodixit.databinding.AdapterStatusUserBinding

class StatusUserAdapter : RecyclerView.Adapter<StatusUserAdapter.ViewHolder>() {
    class ViewHolder(binding: AdapterStatusUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val vBinding = binding
    }

    private val list: ArrayList<UserDocument> = ArrayList()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapter_status_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vm = StatusUserViewModel(list[position])
        holder.vBinding.vm = vm
        holder.vBinding.root.setOnClickListener { v -> if (vm.enabled) pItemListener.let { it(list[position]) } }
    }

    fun replace(items: List<UserDocument>?) {
        list.clear()
        items?.let { list.addAll(items) }
        notifyDataSetChanged()
    }

    private lateinit var pItemListener: (UserDocument) -> Unit
    fun setOnItemListener(l: (UserDocument) -> Unit) {
        pItemListener = l
    }
}

class StatusUserViewModel constructor(user: UserDocument) {
    val name = user.name
    val enabled = user.visible
    var statusImage: Int

    init {
        statusImage = when {
            user.index == -1 -> R.drawable.ic_notready
            user.visible -> R.drawable.ic_visible
            else -> R.drawable.ic_selected
        }
    }
}