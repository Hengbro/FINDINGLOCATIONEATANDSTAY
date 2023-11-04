package com.inyonghengki.findinglocation.ui.searchplace.adabter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.HistorySearch
import com.inyonghengki.findinglocation.databinding.ItemSearchBinding
import com.inyonghengki.findinglocation.ui.searchplace.ResultHasilActivity

class HistorySearchAdapter(

    val onDelete: (item: HistorySearch, pos: Int) -> Unit

) : RecyclerView.Adapter<HistorySearchAdapter.ViewHolder>(){

    private var data = ArrayList<HistorySearch>()

    inner class ViewHolder(private val itemBinding: ItemSearchBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(item : HistorySearch, position: Int){
            itemBinding.apply {
                tvName.text = item.name

                val context = root.context
                layout.setOnClickListener {
                    val message = tvName.text.toString()
                    val intent = Intent(context, ResultHasilActivity::class.java)
                    intent.apply {
                        putExtra("themessage", message)
                    }
                    context.startActivity(intent)
                }

                imageDelete.setOnClickListener {
                    onDelete.invoke(item, adapterPosition)
                }
            }
        }
    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<HistorySearch>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}

