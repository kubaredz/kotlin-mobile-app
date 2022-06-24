package pl.edu.pja.project2.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import pl.edu.pja.project2.R
import pl.edu.pja.project2.databinding.FragmentAuthBinding
import pl.edu.pja.project2.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore
    lateinit var recyclerView: RecyclerView
    lateinit var eventList: ArrayList<EventModel>
    lateinit var homeAdapter: HomeAdapter

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

        eventChangeListener()
        saveFireStore()
    }

    private fun onItemSave(eventName: String, localisation: String, image: ImageView, date: String){
        eventList.add(EventModel(eventName, localisation, image, date, "", ""))
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