package com.example.subsmission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.subsmission2.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Phone"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val photo = intent.getStringExtra("photo")
        val secondDescription = intent.getStringExtra("secondDescription")

        Glide.with(this)
            .load(photo)
            .fitCenter()
            .into(binding.imagePhoto)
        binding.tvTitle.text = name
        binding.tvDescription.text = description
        binding.tvSecondDescription.text = secondDescription

    }

    fun shareToWhatsApp(view: View) {
        val title = binding.tvTitle.text.toString()
        val description = binding.tvDescription.text.toString()
        val secondDescription = binding.tvSecondDescription.text.toString()
        val shareText = "$title\n\n$description\n\n$secondDescription"

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
            `package` = "com.whatsapp"
        }

        startActivity(Intent.createChooser(intent, "Share via"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}