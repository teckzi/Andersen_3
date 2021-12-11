package com.tck.andersen_3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tck.andersen_3.databinding.FragmentTaskTwoBinding
import java.util.concurrent.Executors


class TaskTwo : Fragment() {

    private lateinit var binding : FragmentTaskTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_task_two, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.imageButton.setOnClickListener {

            //glideImageLoad()

            defaultImageLoad()

        }
    }

    private fun glideImageLoad(){
        binding.imageProgressbar.visibility = View.VISIBLE
        Glide
            .with(this)
            .load(binding.imageEditText.text.toString())
            .error(Toast.makeText(context, "Your image link is incorrect", Toast.LENGTH_SHORT).show())
            .into(binding.imageView)
        clearEditText()
    }

    private fun defaultImageLoad(){
        binding.imageProgressbar.visibility = View.VISIBLE
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap?
        executor.execute {
            try {
                val imageSearch = java.net.URL(binding.imageEditText.text.toString()).openStream()
                image = BitmapFactory.decodeStream(imageSearch)
                handler.post {
                    binding.imageView.setImageBitmap(image)
                    clearEditText()
                }
            }
            catch (e: Exception){
                requireActivity().runOnUiThread {
                    clearEditText()
                    Toast.makeText(requireActivity(),"Your image link is incorrect",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun clearEditText(){
        binding.imageEditText.text.clear()
        binding.imageProgressbar.visibility = View.INVISIBLE
    }
}

