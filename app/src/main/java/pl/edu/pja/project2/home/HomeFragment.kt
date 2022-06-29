package pl.edu.pja.project2.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.pja.project2.R
import pl.edu.pja.project2.auth.AuthFragmentDirections
import pl.edu.pja.project2.databinding.FragmentAuthBinding
import pl.edu.pja.project2.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore
    lateinit var recyclerView: RecyclerView
    lateinit var eventList: ArrayList<EventModel>
    lateinit var homeAdapter: HomeAdapter

    private val args: HomeFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        recyclerView = binding.eventListRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        eventList = arrayListOf()
        homeAdapter = HomeAdapter(eventList, ::onItemSave)
        recyclerView.adapter = homeAdapter

//        eventChangeListener()
//        saveFireStore()
        navigateToAddEvent()

    }

    private fun onItemSave(eventName: String, localisation: String, image: ImageView, date: String){
        eventList.add(EventModel(eventName, localisation, image, date, "", ""))
    }

    private fun navigateToAddEvent(){
        binding.addNewEventButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddEventFragment(args.email))
        }
    }


    private fun saveFireStore() {
        TODO("Not yet implemented")
    }

    private fun eventChangeListener() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}