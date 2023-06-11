package com.zeroone.recyclo.ui.dashboard.goods

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.zeroone.recyclo.R
import com.zeroone.recyclo.api.response.DataItem
import com.zeroone.recyclo.dataStore
import com.zeroone.recyclo.databinding.FragmentGoodsBinding
import com.zeroone.recyclo.model.SessionPreference
import com.zeroone.recyclo.ui.dashboard.DashboardViewModel
import com.zeroone.recyclo.ui.dashboard.ViewModelFactory
import com.zeroone.recyclo.ui.dashboard.goods.add.AddActivity
import com.zeroone.recyclo.ui.longlist.LongListAdapter
import com.zeroone.recyclo.ui.longlist.LongListAdapterPagging
import com.zeroone.recyclo.utils.LoadingBar
import com.zeroone.recyclo.utils.SpacesItemDecoration

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GoodsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoodsFragment : Fragment() {
    private lateinit var vm: DashboardViewModel
    private var _binding: FragmentGoodsBinding? = null
    private lateinit var loading : LoadingBar
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGoodsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val fab = view.findViewById<FloatingActionButton>(R.id.addToFav)
        fab.setOnClickListener {
            startActivity(Intent(view.context,AddActivity::class.java))
        }

        loading = LoadingBar(requireActivity())

        val pref = SessionPreference.getInstance(requireActivity().application.dataStore)
        val factory = ViewModelFactory.getInstance(requireActivity().application,pref)
        vm =  ViewModelProvider(this, factory)[DashboardViewModel::class.java]



        vm.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        vm.snackbarText.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let { it1 ->
                Snackbar.make(
                    view.rootView,
                    it1,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        vm.getToken().observe(requireActivity()){
            vm.getGoods(it)
        }
        vm.goods.observe(requireActivity()){
            setGoods(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GoodsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GoodsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.startLoading()
        } else {
            loading.isDismiss()
        }
    }

    private fun setGoods(data:List<DataItem>) {
        var  arrGoods:ArrayList<DataItem> =  ArrayList()
        for (goods in data) {
            arrGoods.add(goods)
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvGoods.layoutManager = layoutManager
        binding.rvGoods.addItemDecoration(SpacesItemDecoration(20))

        val adapter = GoodsAdapter(requireContext(),arrGoods)
        binding.rvGoods.adapter = adapter

    }
}