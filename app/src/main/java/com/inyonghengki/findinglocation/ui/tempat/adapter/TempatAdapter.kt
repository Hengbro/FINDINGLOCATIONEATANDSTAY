package com.inyonghengki.findinglocation.ui.tempat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.Tempat
import com.inyonghengki.findinglocation.databinding.ItemInfoTempatBinding
import com.inyonghengki.findinglocation.util.toUrlTempat
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class TempatAdapter (

    val onClick:(item: Tempat) -> Unit,
    val onClickS:(item: Tempat) -> Unit) :
    RecyclerView.Adapter<TempatAdapter.ViewHolder>(){

    private var data = ArrayList<Tempat>()

    inner class ViewHolder(private val itemBinding: ItemInfoTempatBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : Tempat, position: Int){

            itemBinding.apply {

                tvnmProduk.text = item.nameTempat
                tvNmtempatdua.text = item.nameTempat

                val splitImages = item.imageTempat?.split("|")
                val imageTempatU = if (splitImages.isNullOrEmpty()){
                    item.imageTempat?:""
                }else {
                    splitImages[0]
                }

                imgProduk.setImagePicasso(imageTempatU.toUrlTempat())

                btnTempat.setOnClickListener{
                    onClick.invoke(item)
                }

                btnSetting.setOnClickListener{
                    onClickS.invoke(item)
                }

            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<Tempat>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemInfoTempatBinding.inflate(
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

