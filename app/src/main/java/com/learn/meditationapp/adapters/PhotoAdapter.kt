package com.learn.meditationapp.adapters

import android.graphics.drawable.Drawable
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.learn.meditationapp.Photo
import com.learn.meditationapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class PhotoAdapter(private val list : List<Photo>) : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position == list.size) {
            holder.add?.setOnClickListener {

            }
        }
        else {
            holder.time?.text = list[position].time
            CoroutineScope(Dispatchers.Main).launch {
                var image : Drawable
                withContext(Dispatchers.IO) {
                    image =
                        Drawable.createFromStream(
                            URL(list[position].image).content as InputStream, "src"
                        )
                }
                holder.image?.setImageDrawable(image)
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