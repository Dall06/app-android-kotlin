package com.example.artister.fragments.tab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.artister.R
import com.example.artister.adapters.RecyclerViewAgencyAdapter
import com.example.artister.controllers.UserController
import com.example.artister.models.User
import kotlinx.android.synthetic.main.fragment_tab_agencies.*
import kotlinx.android.synthetic.main.fragment_tab_agencies.swipe_rv
import kotlinx.android.synthetic.main.fragment_tab_artists.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragment_tab_agencies : Fragment() {
    private val agenciesRvAdapter = RecyclerViewAgencyAdapter()
    private lateinit var userController: UserController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_agencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userController = UserController()

        setUpRView(requireContext())
        loadAgenciesAction(requireContext())

        swipe_rv.setOnRefreshListener {
            loadAgenciesAction(requireContext())
            swipe_rv.isRefreshing = false
        }
    }

    private fun setUpRView(context: Context) {
        rv_Agencies.adapter = agenciesRvAdapter
        rv_Agencies.layoutManager = LinearLayoutManager(context)
    }

    private fun loadAgenciesAction (context: Context) {
        val result = userController.getUsersByType(2)

        result.enqueue(object: Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val lstAgencies: List<User>? = response.body()

                    if (lstAgencies != null) {
                        agenciesRvAdapter.setData(lstAgencies)
                    }

                } else {
                    Toast.makeText(context, "Cannot get data", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}