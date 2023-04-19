package com.example.bnetapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bnetapp.R
import com.example.bnetapp.model.retrofit.drugs.DrugsItem
import com.google.android.material.card.MaterialCardView

class DrugsRecyclerItem(private val drugsList: MutableList<DrugsItem>, private val context: Context,
                        onClickListener: OnItemClickListener)
    : RecyclerView.Adapter<DrugsRecyclerItem.MyViewHolder>() {

    private var mainListener: OnItemClickListener = onClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun uploadNewPage()
    }

    class MyViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val cardTitle: TextView = itemView.findViewById(R.id.drug_card_title)
        val cardSecondaryText: TextView = itemView.findViewById(R.id.drug_card_secondary_text)
        val cardImage: ImageView = itemView.findViewById(R.id.drug_card_image)

        init {
            itemView.findViewById<MaterialCardView>(R.id.drug_card).setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.drug_card, parent, false)
        return MyViewHolder(itemView, mainListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == drugsList.size-1) {
            mainListener.uploadNewPage()
        }

        holder.cardTitle.text = drugsList[position].name
        holder.cardSecondaryText.text = drugsList[position].description

        Glide.with(context).load("http://shans.d2.i-partner.ru/${drugsList[position].image}")
            .placeholder(R.drawable.placeholder)
            .into(holder.cardImage)
    }

    override fun getItemCount(): Int {
        return drugsList.size
    }

}
