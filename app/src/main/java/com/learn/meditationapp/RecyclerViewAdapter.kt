package com.learn.meditationapp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class RecyclerViewAdapter(private val list : List<Feelings>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var feelImage : ImageView = itemView.findViewById(R.id.feelImage)
        var feelText : TextView = itemView.findViewById(R.id.feelText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                                     .inflate(R.layout.feelings_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.feelText.text = list[position].feelText
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                holder.feelImage.setImageDrawable(
                    Drawable.createFromStream(
                        URL(list[position].feelImage).content as InputStream, "src"
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}