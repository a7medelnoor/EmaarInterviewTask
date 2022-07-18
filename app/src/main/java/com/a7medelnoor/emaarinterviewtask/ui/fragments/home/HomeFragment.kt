package com.a7medelnoor.emaarinterviewtask.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medelnoor.emaarinterviewtask.R
import com.a7medelnoor.emaarinterviewtask.adapter.UserRecyclerViewAdapter
import com.a7medelnoor.emaarinterviewtask.databinding.FragmentHomeBinding
import com.a7medelnoor.emaarinterviewtask.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    private lateinit var userRecyclerViewAdapter: UserRecyclerViewAdapter




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
        (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar))
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        userRecyclerViewAdapter = UserRecyclerViewAdapter()
        binding.homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = userRecyclerViewAdapter
        }
        binding.homeRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        homeViewMode.userResponse.observe(requireActivity(), { response ->
            Log.d(TAG, "HomeFragment response " + response.results)
            userRecyclerViewAdapter.userData = response.results
        })
    }

}