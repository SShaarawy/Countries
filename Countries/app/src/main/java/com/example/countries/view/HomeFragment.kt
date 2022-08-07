package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.countries.R
import com.example.countries.adapter.HomeAdapter
import com.example.countries.databinding.FragmentHomeBinding
import com.example.countries.model.Country
import com.example.countries.model.CountryModelDB
import com.example.countries.roomdb.CountryDao
import com.example.countries.roomdb.CountryDatabase
import com.example.countries.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), HomeAdapter.Listener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var countryAdapter: HomeAdapter
    private var nameList = arrayListOf<String>()

    private lateinit var db: CountryDatabase
    private lateinit var countryDao: CountryDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameList.clear()


        db = Room.databaseBuilder(requireContext(), CountryDatabase::class.java, "Country")
            .allowMainThreadQueries()
            .build()

        countryDao = db.countryDao()

         val list = countryDao.getAll()

        loadData(list)

        countryAdapter = HomeAdapter(nameList, this)
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHome.adapter = countryAdapter

    }

    private fun loadData(list: List<CountryModelDB>) {

        val call = RetrofitInstance.api.getCountries()
        call.enqueue(object : Callback<Country> {
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                if (response.isSuccessful) {
                    println("DATA:" + response.body()?.data)
                    println("LINKS:" + response.body()?.links)
                    println("METADATA:" + response.body()?.metadata)

                    val data = response.body()?.data

                    if (list.isEmpty()) {
                        for (item in data!!) {
                            countryDao.insert(CountryModelDB(item.name, item.code,false))
                        }
                    }
                    for(item in data!!){
                        nameList.add(item.name)
                    }
                    countryAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Country>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onClick(name: String) {
        val fragment = DetailFragment()

        val bundle = Bundle()
        fragment.arguments = bundle
        bundle.putString("name",name)
        bundle.putInt("sign",0)

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.flMain,fragment)
            commit()
        }
    }
}

