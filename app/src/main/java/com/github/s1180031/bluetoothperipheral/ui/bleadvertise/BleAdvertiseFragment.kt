package com.github.s1180031.bluetoothperipheral.ui.bleadvertise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.s1180031.bluetoothperipheral.databinding.FragmentBleAdvertiseBinding
import org.koin.android.ext.android.getKoin

class BleAdvertiseFragment : Fragment() {
    private val viewModel: BleAdvertiseFragmentViewModel by viewModels {
        val bleType: BleAdvertiseFragmentArgs by navArgs()
        BleAdvertiseFragmentViewModel.Factory(bleType.bleType, getKoin())
    }

    lateinit var binding: FragmentBleAdvertiseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBleAdvertiseBinding.inflate(inflater, container, false)
        setUpBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreate()
    }

    private fun setUpBinding() {
        viewModel.advertiseStateLiveData.observe(viewLifecycleOwner) {
            binding.textAdvertiseState.text = it
        }
    }
}
