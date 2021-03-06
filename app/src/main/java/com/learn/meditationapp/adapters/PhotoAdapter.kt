package com.learn.meditationapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.learn.meditationapp.R
import com.learn.meditationapp.activities.MainActivity
import com.learn.meditationapp.activities.ShowActivity
import com.learn.meditationapp.photo.TruePhoto
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoAdapter(private val list : MutableList<TruePhoto>) : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var time : TextView? = itemView.findViewById(R.id.time)
        var image : ImageView? = itemView.findViewById(R.id.photo)
        var add : CardView? = itemView.findViewById(R.id.addButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                     .inflate(
                                         if (viewType == R.layout.photo_item)
                                             R.layout.photo_item
                                         else R.layout.button_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == list.size) {
            holder.add?.setOnClickListener {
                getActivity(holder.itemView.context)?.startActivityForResult(
                    Intent(Intent.ACTION_PICK).apply {
                        type = "image/*"
                    },
                    MainActivity.CODE
                )
            }
        }
        else {
            holder.time?.text = list[position].time
            Picasso.with(holder.itemView.context)
                .load(list[position].image)
                .fit()
                .centerCrop()
                .into(holder.image)
            holder.itemView.setOnClickListener {
                holder.itemView.context.startActivity(
                    Intent(holder.itemView.context, ShowActivity::class.java).apply {
                        putExtra("INDEX", holder.adapterPosition)
                    }
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) R.layout.button_item else R.layout.photo_item
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

}