package com.example.recycler004

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.city_row.view.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.setBackgroundColor(Color.YELLOW)


        recyclerView_main.layoutManager = LinearLayoutManager(this)

       // recyclerView_main.adapter = MainAdapter()

        fetchJSON()

    }

    fun fetchJSON(){
        val url = "api.openweathermap.org/data/2.5/weather?q=London&appid=33ee99cda2634f90494ae2de783c151d";

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(response: Response?) {
                val body = response?.body()?.string()
                println(body)

                val gson = GsonBuilder().create()

                val cityFeed  = gson.fromJson(body, CityFeed::class.java)


                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(cityFeed)

                }
            }

            override fun onFailure(request: Request?, e: IOException?) {
                println("Failed to execute request")
            }
        })

    }
}


class CityFeed(val citys : List<City>)

class City(val id : Int , val name : String)

class Weather(val main : String)

 class MainAdapter(val cityFeed : CityFeed) : RecyclerView.Adapter<CustomViewHolder>() {



     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
       //  TODO("Not yet implemented")

         val layoutInflater = LayoutInflater.from(parent?.context)

         val cellForRow = layoutInflater.inflate(R.layout.city_row, parent, false)

         return CustomViewHolder(cellForRow)

     }

     override fun getItemCount(): Int {
         // TODO("Not yet implemented")

         return cityFeed.citys.count()
     }

     override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
          // TODO("Not yet implemented")

         val city = cityFeed.citys[position]
         holder.itemView?.cityName?.text = city.name

     }

 }

class CustomViewHolder(view: View, var city:CityInfo? = null) : RecyclerView.ViewHolder(view){

    companion object {

        val LOGCAT_CATEGORY = "JSON"
        val DETAIL_TITLE_KEY = "ActionBarTitle"
        val CITY_NAME = "CITY_NAME"

    }


    init {
        view.setOnClickListener {

            Log.i(LOGCAT_CATEGORY,"Recycler view Item has been clicked")
          Log.i(LOGCAT_CATEGORY, "Name is " + city?.name)
            Log.i(LOGCAT_CATEGORY, "Weather is " + city?.weather)

            val intent = Intent(view.context, RecyclerDetailActivity::class.java)


            intent.putExtra(DETAIL_TITLE_KEY,"Details on " + city?.name )
            intent.putExtra(CITY_NAME, city?.weather)
            

            view.context.startActivity(intent)
        }

    }


}
