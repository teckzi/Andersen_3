package com.tck.andersen_3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.Executors


class TaskTwo : Fragment() {

    private lateinit var loadButton:ImageButton
    private lateinit var imageView: ImageView
    private lateinit var urlEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_task_two, container, false)

        loadButton = view.findViewById(R.id.loadButton)
        imageView = view.findViewById(R.id.imageView)
        urlEditText = view.findViewById(R.id.imageEditText)

        return view
    }

    override fun onStart() {
        super.onStart()
        loadButton.setOnClickListener {

            //glideImageLoad()

            defaultImageLoad()
        }
    }

    private fun glideImageLoad(){
        Glide
            .with(this)
            .load(urlEditText.text.toString())
            .placeholder(loadingPlaceHolder())
            .error(R.drawable.ic_baseline_broken_image_24)
            .fallback(R.drawable.ic_baseline_image_24)
            .error(Toast.makeText(requireContext(),"Your image link is incorrect",Toast.LENGTH_SHORT).show())
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    private fun defaultImageLoad(){
        imageView.setImageDrawable(loadingPlaceHolder())

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap?
        executor.execute {
            try {
                val imageSearch = java.net.URL(urlEditText.text.toString()).openStream()
                image = BitmapFactory.decodeStream(imageSearch)
                handler.post {
                    imageView.setImageBitmap(image)
                }
            }
            catch (e: Exception){
                requireActivity().runOnUiThread {
                    imageView.setImageResource(R.drawable.ic_baseline_broken_image_24)
                    Toast.makeText(requireActivity(),"Your image link is incorrect",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadingPlaceHolder() : CircularProgressDrawable{
        val drawable = CircularProgressDrawable(requireContext())
        drawable.setColorSchemeColors(
            R.color.design_default_color_primary,
            R.color.design_default_color_on_secondary,
            R.color.design_default_color_primary_variant
        )
        drawable.centerRadius = 90f
        drawable.strokeWidth = 15f
        drawable.start()

        return drawable
    }
}

