package com.a7medelnoor.emaarinterviewtask.ui.fragments.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medelnoor.emaarinterviewtask.R
import com.a7medelnoor.emaarinterviewtask.adapter.UserRecyclerViewAdapter
import com.a7medelnoor.emaarinterviewtask.databinding.FragmentHomeBinding
import com.a7medelnoor.emaarinterviewtask.util.NetworkListener
import com.a7medelnoor.emaarinterviewtask.util.NetworkResult
import com.a7medelnoor.emaarinterviewtask.util.observeOnce
import com.a7medelnoor.emaarinterviewtask.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

/**
 ** @author Ahmed Elnoor
 ** Email: ahdnoor4@gmail.com
 ** Github: @a7medelnoor
 ** @since 18/07/2022
 ** This Assessment for Emaar Android developer interview task
 ** IT people Gulf
 ** Don't use this code for commercial propose
 **/
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewMode: HomeViewModel by viewModels()
    private val userRecyclerViewAdapter by lazy { UserRecyclerViewAdapter() }
    private lateinit var networkListener: NetworkListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar_user_details))
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    homeViewMode.networkStatus = status
                    Log.d(TAG, "NetworkListener status " + status.toString())
                    readDataBase()
                }
        }

    }


    @ExperimentalCoroutinesApi
    private fun setupRecyclerView() {
        binding.homeRecyclerView.adapter = userRecyclerViewAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.homeRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun readDataBase() {
        lifecycleScope.launch {
            homeViewMode.readUserDataFromDataSource.observeOnce(viewLifecycleOwner, { database ->
                // check database is empty or not
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readdatabase called")

                    // read from our database
                    userRecyclerViewAdapter.setData(database[0].userData)
                } else {
                    requestDataFromApi()
                }
            })
        }
    }

    private fun readDataFromCache() {
        lifecycleScope.launch {
            homeViewMode.readUserDataFromDataSource.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    userRecyclerViewAdapter.setData(database[0].userData)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestDataFromApi() {
        homeViewMode.getUsers()
        homeViewMode.user_Response.observe(viewLifecycleOwner, { response ->
            Log.d(TAG, "HomeFragment response " + response)
            when (response) {
                is NetworkResult.SUCCESS -> {
                    response.data?.let {
                        userRecyclerViewAdapter.setData(it)
                    }
                }
                is NetworkResult.ERROR -> {
                    Log.d(TAG, "HomeFragment error " + response)

                    readDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


    }
}