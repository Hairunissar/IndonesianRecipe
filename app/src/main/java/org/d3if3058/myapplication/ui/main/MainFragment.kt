package org.d3if3058.myapplication.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.d3if3058.myapplication.MainActivity
import org.d3if3058.myapplication.R
import org.d3if3058.myapplication.databinding.FragmentMainBinding
import org.d3if3058.myapplication.model.Resep
import org.d3if3058.myapplication.network.ApiStatus

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var myAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        myAdapter = MainAdapter { resep ->
            val bundle = Bundle().apply {
                putString("nama", resep.nama)
                putString("imageId", resep.imageId)
//                putString("deskripsi", resep.deskripsi)
//                putString("bahan", resep.bahan)
//                putString("intruksi", resep.intruksi)
            }
            findNavController().navigate(R.id.fragmentResep, bundle)
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.setHasFixedSize(true)

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentHitungKalori)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) { newData ->
            myAdapter.updateData(newData)
        }

        viewModel.getStatus().observe(viewLifecycleOwner) { status ->
            updateProgress(status)
        }

        viewModel.scheduleUpdater(requireActivity().application)
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }
}
