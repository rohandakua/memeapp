package com.example.memekingdom

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    var currentMemeUrl: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Snackbar.make(
            findViewById(R.id.memeView),
            "CAN CONTAIN ADULT MEMES , USER'S DISCRETION IS ADVISED",
            Snackbar.LENGTH_SHORT
        ).show()
        loadMeme()
    }
    private fun loadMeme() {

        val progressbar=findViewById<ProgressBar>(R.id.progress)
        progressbar.visibility=View.VISIBLE
        val queue=Volley.newRequestQueue(this)

        val url = "https://meme-api.com/gimme"


        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                var currentMemeUrl = response.getString("url")

                Glide.with(this).load(currentMemeUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false

                    }
                }).into(findViewById<ImageView>(R.id.memeView))
            },
            {

                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    fun next(view: View) {
        loadMeme()
    }

    fun share(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey !!!!! checkout this exiting meme that I got from Reddit ... $currentMemeUrl")
        val chooser = Intent.createChooser(intent,"share this meme using ...")
        startActivity(chooser)
    }
}