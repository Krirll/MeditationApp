package com.learn.meditationapp.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.learn.meditationapp.API.RetrofitObject
import com.learn.meditationapp.DataBase
import com.learn.meditationapp.Photo
import com.learn.meditationapp.R
import com.learn.meditationapp.adapters.PhotoAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

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
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        CoroutineScope(Dispatchers.Main).launch {
            val dataBase : DataBase
            val list : List<Photo>
            withContext(Dispatchers.IO) {
                dataBase =
                    Room.databaseBuilder(view.context, DataBase::class.java, "photos")
                        .build()
                list = dataBase.photoDao().getAll()
            }
            recyclerView.adapter = PhotoAdapter(list)
        }
    }
}