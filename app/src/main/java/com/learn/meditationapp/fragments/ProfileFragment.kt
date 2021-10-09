package com.learn.meditationapp.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.meditationapp.R
import com.learn.meditationapp.adapters.PhotoAdapter
import com.learn.meditationapp.photo.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            val image : Drawable
            withContext(Dispatchers.IO) {
                image =
                    Drawable.createFromStream(
                        URL(requireArguments().getString("AVATAR")).content as InputStream, "src"
                    )
            }
            view.findViewById<ImageView>(R.id.avatarProfile).setImageDrawable(image)
        }
        view.findViewById<TextView>(R.id.nickProfile).text = requireArguments().getString("NAME")
        val recyclerView = view.findViewById<RecyclerView>(R.id.photos)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        CoroutineScope(Dispatchers.Main).launch {
            val list : MutableList<Photo> = mutableListOf()
            withContext(Dispatchers.IO) {
                //list = ActualDataBase.getAll()
            }
            recyclerView.adapter = PhotoAdapter(list as MutableList<Photo>, recyclerView)
        }
    }
}