package org.d3if3058.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if3058.myapplication.R
import org.d3if3058.myapplication.databinding.FragmentHitungKaloriBinding

class HitungKaloriFragment : Fragment() {

    private lateinit var binding: FragmentHitungKaloriBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHitungKaloriBinding.inflate(layoutInflater, container, false)

        binding.calculateButton.setOnClickListener {
            val age = binding.ageEditText.text.toString().toInt()
            val gender = if (binding.genderRadioGroup.checkedRadioButtonId == R.id.maleRadioButton) "Laki-laki" else "Perempuan"
            val height = binding.heightEditText.text.toString().toDouble()
            val weight = binding.weightEditText.text.toString().toDouble()

            val bmr = calculateBMR(age, gender, height, weight)
            val result = "Kebutuhan kalori harian Anda: $bmr kalori"
            binding.resultTextView.text = result
        }

        return binding.root
    }

    private fun calculateBMR(age: Int, gender: String, height: Double, weight: Double): Double {
        var bmr = 0.0

        if (gender == "Laki-laki") {
            bmr = 66 + (13.75 * weight) + (5 * height) - (6.75 * age)
        } else if (gender == "Perempuan") {
            bmr = 655 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
        }

        return bmr
    }
}
