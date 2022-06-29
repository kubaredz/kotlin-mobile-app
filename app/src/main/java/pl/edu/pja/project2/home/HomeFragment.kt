package pl.edu.pja.project2.home

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.edu.pja.project2.FirebaseStorageManager
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
//    val setGoalsData = MutableStateFlow<ArrayList<String>>(arrayListOf())
var setGoalsData = MutableStateFlow(0)
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
        homeAdapter = HomeAdapter(eventList, ::onItemSave, ::onItemClicked)
        recyclerView.adapter = homeAdapter

//        eventChangeListener()
//        saveFireStore()
        navigateToAddEvent()
        fetchData()
        refreshRecyclerView()
        refreshData()
    }

    private fun onItemClicked() {
        // TODO
        // NAVIGATE FURTHER i SET DATA ARGUMENTS
    }

    private fun refreshRecyclerView() {

        // arraylista z elementami
//        CoroutineScope(Dispatchers.Main).launch {
//            setGoalsData.collect {
        // TODO Flow automatycznie trigerrowany
        binding.btnRefresh.setOnClickListener {
                homeAdapter.items = FirebaseStorageManager.elementList
                Log.d("value", "${homeAdapter.items}")
                homeAdapter.notifyDataSetChanged()
            val ft = parentFragmentManager.beginTransaction()
            ft.detach(this).attach(this).commit()
//            }
        }

        //    document.data.getValue("author").toString(),
//    document.data.getValue("eventTitle").toString(),
//    document.data.getValue("description").toString(),
//    document.data.getValue("imagePath").toString(),
//    document.data.getValue("data").toString()
    }
    private fun refreshData() {
        homeAdapter.items = FirebaseStorageManager.elementList
        Log.d("value", "${homeAdapter.items}")
        homeAdapter.notifyDataSetChanged()
        val ft = parentFragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }

    private fun onItemSave(eventName: String, localisation: String, image: ImageView, date: String){
        eventList.add(EventModel(eventName, localisation, image, date, "", ""))
    }

    private fun navigateToAddEvent(){
        binding.addNewEventButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddEventFragment(args.email))
        }
    }

    fun fetchData(){
        FirebaseStorageManager().readFireStoreData()
        refreshRecyclerView()
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