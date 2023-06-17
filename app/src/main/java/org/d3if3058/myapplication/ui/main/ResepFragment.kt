package org.d3if3058.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.d3if3058.myapplication.R
import org.d3if3058.myapplication.databinding.FragmentResepBinding
import org.d3if3058.myapplication.network.ResepApi

class ResepFragment : Fragment() {
    private lateinit var binding: FragmentResepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val nama = arguments?.getString("nama")
        val deskripsi = arguments?.getString("deskripsi")
        val bahan = arguments?.getString("bahan")
        val intruksi = arguments?.getString("intruksi")
        val imageId = arguments?.getString("imageId")

        // Tampilkan gambar makanan dan nama makanan di layout ResepFragment
        Glide.with(requireContext())
            .load(imageId?.let { ResepApi.getResepUrl(it) })
            .error(R.drawable.baseline_broken_image_24)
            .into(binding.fotoMakanan)

        binding.namaMakanan.text = nama
//        binding.deskripsiMakanan.text = deskripsi
//        binding.isiBahanBahan.text = bahan
//        binding.isiIntruksi.text = intruksi


        // Ambil string ID berdasarkan nama makanan
        val isiInstruksiId = resources.getIdentifier("isi_intruksi_$nama", "string", requireContext().packageName)
        val isiBahanBahanId = resources.getIdentifier("isi_bahan_bahan_$nama", "string", requireContext().packageName)
        val deskripsiMakananId = resources.getIdentifier("deskripsi_makanan_$nama", "string", requireContext().packageName)

        // Set teks pada TextView berdasarkan string ID
        binding.isiIntruksi.setText(isiInstruksiId)
        binding.isiBahanBahan.setText(isiBahanBahanId)
        binding.deskripsiMakanan.setText(deskripsiMakananId)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}