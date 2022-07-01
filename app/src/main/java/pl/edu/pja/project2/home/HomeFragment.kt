package pl.edu.pja.project2.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    lateinit var fireBaseManager: FirebaseStorageManager

    //    val setGoalsData = MutableStateFlow<ArrayList<String>>(arrayListOf())
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
        fireBaseManager = FirebaseStorageManager()
        db = FirebaseFirestore.getInstance()
        recyclerView = binding.eventListRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        eventList = arrayListOf()
        homeAdapter = HomeAdapter(eventList, ::onItemSave, ::onItemClicked)
        recyclerView.adapter = homeAdapter
        homeAdapter.items = arrayListOf(arrayListOf())
        homeAdapter.notifyDataSetChanged()

//        eventChangeListener()
//        saveFireStore()
        navigateToAddEvent()
//        refreshRecyclerView()
//        refreshData()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun onItemClicked() {
//        findNavController().navigate(HomeFragmentDirections.)
        // TODO
        // NAVIGATE FURTHER i SET DATA ARGUMENTS
    }

    private fun refreshRecyclerView() {
        // arraylista z elementami
        homeAdapter.items.clear()
            lifecycleScope.launch {
                FirebaseStorageManager.setGoalsData.collect {
                    if (it.isEmpty()) {
                        fireBaseManager.readFireStoreData()
                        Log.d("value", "EMPTY ${it}")
                        homeAdapter.items.clear()
//                        homeAdapter.items = arrayListOf(arrayListOf())
                        homeAdapter.notifyDataSetChanged()
                    } else {
                        homeAdapter.items.clear()
                        homeAdapter.notifyDataSetChanged()
                        homeAdapter.items = it
                        Log.d("value", "FLOW REPEAT ${it}")
                        homeAdapter.notifyDataSetChanged()
                    }
                }
                fireBaseManager.readFireStoreData()
                // TODO Flow automatycznie trigerrowany
        }
//        binding.btnRefresh.setOnClickListener {
//            fireBaseManager.readFireStoreData()
//            homeAdapter.items = arrayListOf()
//            homeAdapter.notifyDataSetChanged()
//            homeAdapter.items = FirebaseStorageManager.elementList
//            homeAdapter.notifyDataSetChanged()
//            refreshData()
////            }
//        }
    }

    private fun refreshData() {
//        homeAdapter.items = FirebaseStorageManager.elementList
//        Log.d("value", "${homeAdapter.items}")
//        homeAdapter.notifyDataSetChanged()
        val ft = parentFragmentManager.beginTransaction()
        ft.detach(this).attach(this).commit()
    }

    private fun onItemSave(
        eventName: String,
        localisation: String,
        image: ImageView,
        date: String
    ) {
        eventList.add(EventModel(eventName, localisation, image, date, "", ""))
    }

    private fun navigateToAddEvent() {
        binding.addNewEventButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddEventFragment(
                    args.email
                )
            )
        }
    }

    fun fetchData() {
//        FirebaseStorageManager().readFireStoreData()
        refreshRecyclerView()
//        refreshRecyclerView()
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