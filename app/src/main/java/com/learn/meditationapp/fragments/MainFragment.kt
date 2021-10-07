package com.learn.meditationapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learn.meditationapp.API.Feel
import com.learn.meditationapp.API.Feelings
import com.learn.meditationapp.API.RetrofitObject
import com.learn.meditationapp.R
import com.learn.meditationapp.adapters.MainAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.welcomeTextView).text =
            view.resources.getString(R.string.welcome, requireArguments().getString("NAME"))
        val recyclerView = view.findViewById<RecyclerView>(R.id.feelings)
        recyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        CoroutineScope(Dispatchers.Main).launch {
            val list : MutableList<Feel> = mutableListOf()
            withContext(Dispatchers.IO) {
                val call = RetrofitObject.service?.getFeelings()
                call?.enqueue(object : Callback<Feelings> {
                    override fun onResponse(call: Call<Feelings>,
                                            response: Response<Feelings>) {
                        if (response.isSuccessful && response.body() != null) {
                            Log.d("RESPONSE", "success")
                            for (feel in response.body()?.data!!) {
                                list.add(feel)
                            }
                            recyclerView.adapter = MainAdapter(list)
                        }
                    }

                    override fun onFailure(call: Call<Feelings>, t: Throwable) {
                        Log.d("FAILURE", t.localizedMessage!!)
                    }
                })
            }
        }
    }
}