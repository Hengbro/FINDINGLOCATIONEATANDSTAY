package com.inyonghengki.findinglocation.ui.menufasilitas.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.inyonghengki.findinglocation.core.data.source.model.MenuFasilitas
import com.inyonghengki.findinglocation.databinding.ItemFasilitasBinding
import com.inyonghengki.findinglocation.util.toUrlFasilitas
import com.inyongtisto.myhelper.extension.setImagePicasso


@SuppressLint("NotifyDataSetChanged", "SetTextI18n")
class MenuFasilitasAdapter (

    val onClick:(item: MenuFasilitas) -> Unit,
    val onDelete:(item: MenuFasilitas, pos: Int) -> Unit):
    RecyclerView.Adapter<MenuFasilitasAdapter.ViewHolder>(){

    private var data = ArrayList<MenuFasilitas>()

    inner class ViewHolder(private val itemBinding: ItemFasilitasBinding): RecyclerView.ViewHolder(itemBinding.root){

        fun bind(item : MenuFasilitas, position: Int){

            itemBinding.apply {

                tvnmProduk.text = item.name
                tvQty.text = item.stock.toString()
                tvDeskripsi.text = item.description

                val splitImages = item.image?.split("|")
                val imageProduct = if (splitImages.isNullOrEmpty()){
                    item.image?:""
                }else {
                    splitImages[0]
                }

                imgProduk.setImagePicasso(imageProduct.toUrlFasilitas())

                val context = root.context

                btnDelete.setOnClickListener{
                    val dialog = AlertDialog.Builder(context)
                    dialog.apply {
                        setTitle("Konfirmasi hapus produk")
                        setMessage("Apakah anda yakin untuk menghapus produk ini?")
                        setPositiveButton("HAPUS"){ dialogInterface, i  ->
                            onDelete.invoke(item, adapterPosition)
                            dialogInterface.dismiss()
                        }
                        setNegativeButton("BATAL"){ dialogInterface, c ->
                            dialogInterface.dismiss()
                        }
                        dialog.show()
                    }

                }

                btnEdit.setOnClickListener{
                    onClick.invoke(item)
                }

            }
        }

    }

    fun removeAt(index: Int) {
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItems(items : List<MenuFasilitas>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFasilitasBinding.inflate(
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

