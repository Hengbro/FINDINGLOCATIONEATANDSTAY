package com.inyonghengki.findinglocation.ui.searchplace.adabter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("NotifyDataSetChanged")
class RecyclerViewAdapter(
    private var dataList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    fun search(query: String) {
        // Lakukan pencarian sesuai dengan query
        // Misalnya, filter dataList berdasarkan query
        val filteredList = dataList.filter { it.contains(query, true) }
        // Update dataList dengan hasil pencarian
        dataList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.textView.text = data
        holder.itemView.setOnClickListener { onItemClick(data) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }
}


