package com.example.moviesapp.ui.tv

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.adapter.AdapterTv
import com.example.moviesapp.databinding.FragmentTvBinding
import com.example.moviesapp.model.TvShow
import com.example.moviesapp.tvDetails.TvDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!
    private var tvListItem: ArrayList<TvShow> = ArrayList()
    private lateinit var tvViewModel: TvViewModel
    private lateinit var adapterTv: AdapterTv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvViewModel =
            ViewModelProvider(this)[TvViewModel::class.java]
        adapterTv = AdapterTv(context, tvListItem)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        untilizeRecyclerTv()
        tvViewModel.getTvListItem("c7904664dab1032728ba852ebc8c8c50")
        observerGetTvList()
        tvViewModel.getLocalTvs()
        observerGetTvLocalList()
        onTvShowClick()
    }

    private fun untilizeRecyclerTv() {
        binding.recyclerTv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterTv
        }
    }

    private fun observerGetTvLocalList() {
        tvViewModel.getTvLiveData().observe(viewLifecycleOwner) { tvListItemLocal ->
            val tvItemArrayList = ArrayList(tvListItemLocal)
            if (tvItemArrayList.size > 0) {
                adapterTv.updateListTv(tvItemArrayList)
            }
        }

    }

    @SuppressLint("LogNotTimber")
    private fun observerGetTvList() {
        tvViewModel.getTv().observe(viewLifecycleOwner) { tvList ->
            tvListItem = ArrayList(tvList)
            for (item in tvList) {
                viewLifecycleOwner.lifecycleScope.launch {
                    tvViewModel.insertTvs(item)
                }
            }
            if (tvListItem.size > 0) {
                adapterTv.updateListTv(tvListItem)
                Log.i("musa", "$tvListItem")
                adapterTv.updateListTv(tvListItem)
            } else {
                Log.i("musa", "$tvListItem")

            }

        }
    }

    private fun onTvShowClick() {
        adapterTv.onItemClick = { tvShow ->
          val intent =  Intent(activity,TvDetails::class.java)
            intent.putExtra("tvId",tvShow.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}