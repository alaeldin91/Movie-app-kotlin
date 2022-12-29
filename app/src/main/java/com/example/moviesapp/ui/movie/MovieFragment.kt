package com.example.moviesapp.ui.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.adapter.AdapterMovie
import com.example.moviesapp.databinding.FragmentMovieBinding
import com.example.moviesapp.model.Movie
import com.example.moviesapp.movieDetails.MovieDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var movieListItem: ArrayList<Movie> = ArrayList()
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapterMovie: AdapterMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel =
            ViewModelProvider(this)[MovieViewModel::class.java]
        adapterMovie = AdapterMovie(context, movieListItem)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        untilizeRecyclerMovie()
        movieViewModel.getMovieListItem("c7904664dab1032728ba852ebc8c8c50")
        observerMovie()
        movieViewModel.getLocalMovies()
        observerLocalMovie()
        movieOnClick()
    }

    private fun untilizeRecyclerMovie() {
        binding.recyclerMovie.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = adapterMovie
        }
    }

    private fun observerMovie() {
        movieViewModel.getMovie().observe(
            viewLifecycleOwner
        ) { movieList ->
            movieListItem = ArrayList(movieList)
            for (item in movieList) {
                viewLifecycleOwner.lifecycleScope.launch {
                    movieViewModel.insertMovies(item)
                }
            }
            adapterMovie.updateList(movieListItem)
        }
    }

    private fun observerLocalMovie() {
        movieViewModel.getLiveDataLocalMovies().observe(viewLifecycleOwner) { movieListItemLocal ->
            val movieListItemArray = ArrayList(movieListItemLocal)
            if (movieListItemArray.size > 0) {
                adapterMovie.updateList(movieListItemArray)
            }

        }
    }

    @SuppressLint("LogNotTimber")
    private fun movieOnClick() {
        adapterMovie.onItemClick = { movie ->
            val intent = Intent(activity, MovieDetails::class.java)
            var movieId = movie.id
            intent.putExtra("movieId", movieId)
            startActivity(intent)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}