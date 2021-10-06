package com.learn.meditationapp.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.meditationapp.API.Feel
import com.learn.meditationapp.API.Feelings
import com.learn.meditationapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class MainAdapter(private val list : List<Feel>) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

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
        holder.feelText.text = list[position].title
        CoroutineScope(Dispatchers.Main).launch {
            val image : Drawable
            withContext(Dispatchers.IO) {
                image =
                    Drawable.createFromStream(
                        URL(list[position].image).content as InputStream,
                        "src"
                    )
            }
            holder.feelImage.setImageDrawable(image)
        }
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}