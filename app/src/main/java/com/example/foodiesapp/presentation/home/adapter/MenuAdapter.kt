package com.example.foodiesapp.presentation.home.adapter

import MenuGridItemViewHolder
import MenuListItemViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodiesapp.base.ViewHolderBinder
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.databinding.ItemGridMenuBinding
import com.example.foodiesapp.databinding.ItemListMenuBinding

class MenuAdapter(
    private val listener: OnItemClickedListener<Menu>,
    var listMode: Int = MODE_LIST
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MODE_LIST = 1
        const val MODE_GRID = 0
    }

    private var asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    fun submitData(data: List<Menu>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == MODE_GRID) {
            val binding = ItemGridMenuBinding.inflate(inflater, parent, false)
            MenuGridItemViewHolder(binding, listener)
        } else {
            val binding = ItemListMenuBinding.inflate(inflater, parent, false)
            MenuListItemViewHolder(binding, listener)
        }
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderBinder<*>) {
            (holder as ViewHolderBinder<Menu>).bind(asyncDataDiffer.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listMode
    }

    interface OnItemClickedListener<T> {
        fun onItemClicked(item: T)
    }
}
