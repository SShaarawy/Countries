package com.example.countries.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.countries.R
import com.example.countries.databinding.RecyclerRowBinding
import com.example.countries.roomdb.CountryDao
import com.example.countries.roomdb.CountryDatabase

class HomeAdapter(private var nameList: ArrayList<String>, private val listener: Listener) : RecyclerView.Adapter<HomeAdapter.RowHolder>() {

    private lateinit var db : CountryDatabase
    private lateinit var countryDao : CountryDao

    interface Listener {
        fun onClick(name : String)
    }

    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        db = Room.databaseBuilder(holder.itemView.context,CountryDatabase::class.java,"Country")
            .allowMainThreadQueries()
            .build()

        countryDao = db.countryDao()

        val eName = nameList[position]

        holder.binding.tvName.text = eName

        if(countryDao.getStateFromName(eName)){
            holder.binding.icStar.setImageResource(R.drawable.ic_star_pressed)
        } else {
            holder.binding.icStar.setImageResource(R.drawable.ic_star_not_pressed)
        }

        holder.itemView.setOnClickListener {
            listener.onClick(eName)
        }

        holder.binding.icStar.setOnClickListener {
            if(!countryDao.getStateFromName(eName)){
                holder.binding.icStar.setImageResource(R.drawable.ic_star_pressed)
                countryDao.updateState(eName,true)

            } else {
                holder.binding.icStar.setImageResource(R.drawable.ic_star_not_pressed)
                countryDao.updateState(eName,false)
            }
        }
    }

    override fun getItemCount(): Int {
       return  nameList.size
    }
}