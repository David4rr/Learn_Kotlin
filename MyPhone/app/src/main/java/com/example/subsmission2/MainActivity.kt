package com.example.subsmission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subsmission2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Gadget>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGadget.setHasFixedSize(true)

        list.addAll(listGadget)
        showRecyclerList()
    }

    private val listGadget: ArrayList<Gadget>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataSecondDescription = resources.getStringArray(R.array.second_description)
            val dataPhoto = resources.getStringArray(R.array.data_photo)
            val listGadget = ArrayList<Gadget>()
            for (i in dataName.indices) {
                val gadget = Gadget(dataName[i], dataDescription[i], dataSecondDescription[i], dataPhoto[i])
                listGadget.add(gadget)
            }
            return listGadget
        }

    private fun showRecyclerList() {
        binding.rvGadget.layoutManager = LinearLayoutManager(this)
        val listGadgetAdapter = ListGadgetAdapter(list)
        binding.rvGadget.adapter = listGadgetAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottombar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                binding.rvGadget.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                binding.rvGadget.layoutManager = GridLayoutManager(this, 2)
            }
            R.id.action_aboutMe -> {
                val aboutintent = Intent(this@MainActivity, AboutMeActivity::class.java)
                startActivity(aboutintent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
