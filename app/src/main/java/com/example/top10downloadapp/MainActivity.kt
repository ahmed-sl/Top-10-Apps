package com.example.top10downloadapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var myRv : RecyclerView
    lateinit var button : Button
    var Top10Apps = mutableListOf<Top10Apps>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRv = findViewById(R.id.recyclerView)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            FetchData().execute()
        }
    }
    private inner class FetchData: AsyncTask<Void, Void, MutableList<Top10Apps>>(){
        val parser = XMLParser()
        override fun doInBackground(vararg p0: Void?): MutableList<Top10Apps> {
            val url = URL("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
            val urlConnection = url.openConnection() as HttpURLConnection
            Top10Apps = urlConnection.getInputStream()?.let {
                parser.parse(it)
            } as MutableList<Top10Apps>
            return Top10Apps
        }

        override fun onPostExecute(result: MutableList<Top10Apps>?) {
            super.onPostExecute(result)
            myRv.adapter = rvAdaptar(Top10Apps)
            myRv.layoutManager = LinearLayoutManager(this@MainActivity)
        }


    }
}
