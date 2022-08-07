package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.countries.R
import com.example.countries.adapter.SavedAdapter
import com.example.countries.databinding.FragmentSavedBinding
import com.example.countries.roomdb.CountryDao
import com.example.countries.roomdb.CountryDatabase

class SavedFragment : Fragment(),SavedAdapter.Listener{
    private lateinit var binding: FragmentSavedBinding
    private var savedList = arrayListOf<String>()
    private lateinit var db: CountryDatabase
    private lateinit var countryDao: CountryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavedBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedList.clear()

        val data = arguments
        data?.getSerializable("savedList")

        db = Room.databaseBuilder(requireContext(), CountryDatabase::class.java, "Country")
            .allowMainThreadQueries()
            .build()

        countryDao = db.countryDao()

        val list = countryDao.getAll()

        for(item in list){
            if(item.isSaved){
                savedList.add(item.name)
            }
        }

        binding.rvSaved.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSaved.adapter = SavedAdapter(savedList,this)
    }

    override fun onClick(name: String) {
        val fragment = DetailFragment()

        val bundle = Bundle()
        fragment.arguments = bundle
        bundle.putString("name",name)
        bundle.putInt("sign",1)

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain,fragment)
            commit()
        }
    }
}