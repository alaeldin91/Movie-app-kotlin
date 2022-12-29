package com.example.moviesapp.ui.artist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.adapter.AdapterArtist
import com.example.moviesapp.artistDetails.ArtistDetails
import com.example.moviesapp.databinding.FragmentArtistBinding
import com.example.moviesapp.model.Artist
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class ArtistFragment : Fragment() {
    var fragment: Fragment? = null
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var artistViewModel: ArtistViewModel
    private lateinit var adapterArtist: AdapterArtist
    private var artistListItem: ArrayList<Artist> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistViewModel = ViewModelProvider(this)[ArtistViewModel::class.java]
        adapterArtist = AdapterArtist(context, artistListItem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        untilizeRecyclerMovie()
        GlobalScope.launch {
            artistViewModel.getArtist("c7904664dab1032728ba852ebc8c8c50")
        }
        artistViewModel.getLocalArtist()
        observerGetArtist()
        observerGetLiveData()
        onItemClickArtist()

    }

    private fun observerGetArtist() {
        artistViewModel.getArtistMutableLiveData()
            .observe(viewLifecycleOwner) { artistList ->
                artistListItem = ArrayList(artistList)
                for (item in artistListItem) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        artistViewModel.insertArtist(item)
                    }
                }

                adapterArtist.updateList(artistListItem)
            }
    }

    private fun observerGetLiveData() {
        artistViewModel.getArtistLiveData().observe(viewLifecycleOwner) { artistListItemLocal ->
            val artistListItem = ArrayList(artistListItemLocal)
            if (artistListItem.size > 0) {
                adapterArtist.updateList(artistListItem)

            }

        }

    }

    private fun untilizeRecyclerMovie() {
        binding.artistRecycler.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterArtist

        }
    }

    private fun onItemClickArtist() {
        adapterArtist.onItemClick = { artist ->
            val intent = Intent(activity, ArtistDetails::class.java)
            intent.putExtra("id", artist.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}