package com.example.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.pokedex.WeatherResponse.Pokemon
import com.example.pokedex.WeatherResponse.PokemonResponse
import com.example.pokedex.adapter.PokemonAdapter
import com.example.pokedex.adapter.onClickListener
import com.example.pokedex.databinding.FragmentListBinding
import com.example.pokedex.service.pokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var _binding: FragmentListBinding? = null
val binding get() = _binding!!


/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false)
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.playgroundList.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_list_to_home)
        }

        DataGeneration(3)


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun DataGeneration(generation: Int?){

        val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(HTTPLogger.getLogger())
                .build()
        val jsonPlaceholderApi = retrofit.create(pokemonService::class.java)
        val myCall: Call<PokemonResponse> = jsonPlaceholderApi.getResponse(generation)


        getData(myCall)
    }

    fun getData(myCall: Call<PokemonResponse>){

        myCall.enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
            ) {
                val DataResponse: PokemonResponse = response.body()!!

                Timber.i("on do RES")
                Timber.i("on do RES %s", DataResponse.pokemon_species )

                val recyclerView =  binding.listRecycleView
                recyclerView.adapter = PokemonAdapter(requireContext(), DataResponse.pokemon_species, object : onClickListener {
                    override fun onClick(position: Int) {
                        Timber.i("clicked %s", position)
                        Timber.i("clicked %s", DataResponse.pokemon_species[position].getNumber())
                    }

                })

                recyclerView.setHasFixedSize(true)
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Timber.i("on do ERROR")
            }

        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


}