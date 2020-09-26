package com.github.s1180031.bluetoothperipheral.ui.selectbletype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.s1180031.bluetoothperipheral.databinding.FragmentSelectBleTypeBinding
import com.github.s1180031.bluetoothperipheral.ui.selectbletype.adapter.SelectBleTypeAdapter
import org.koin.android.ext.android.getKoin

class SelectBleTypeFragment : Fragment() {
    private val viewModel: SelectBleTypeViewModel by viewModels {
        SelectBleTypeViewModel.Factory(getKoin())
    }
    private val adapter: SelectBleTypeAdapter?
        get() {
            return binding.recyclerView.adapter as? SelectBleTypeAdapter
        }
    private lateinit var binding: FragmentSelectBleTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectBleTypeBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = SelectBleTypeAdapter()
        setUpBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreate()
    }

    override fun onResume() {
        super.onResume()
        adapter?.listener = {
            viewModel.onItemSelected(it)
        }
        viewModel.router = SelectBleTypeRouterImpl(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.router = null
        adapter?.listener = null
    }

    private fun setUpBinding() {
        viewModel.bleTypesLiveData.observe(viewLifecycleOwner) {
            adapter?.items = it
        }
    }
}
