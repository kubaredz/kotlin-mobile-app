package pl.edu.pja.project2.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import pl.edu.pja.project2.FirebaseStorageManager
import pl.edu.pja.project2.MainActivity
import pl.edu.pja.project2.databinding.FragmentAddEventBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.math.log

class AddEventFragment : Fragment() {
    lateinit var binding: FragmentAddEventBinding
    private val args: AddEventFragmentArgs by navArgs()
    private var number = 0
    var imgURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

    }

    private fun initUi() {
        binding.addImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .crop()
                .compress(1024)
                .maxResultSize(512, 512)
                .start()
        }
        binding.checkImage.setOnClickListener {
            onRefresh()
            imgURI = Uri.parse(MainActivity.eventImage)
            FirebaseStorageManager().uploadImage(requireContext(), imgURI!!)
        }
        binding.addEvent.setOnClickListener {
//            val imgURI = binding.addEvent.tag as Uri?
            if (imgURI == null) {
                Toast.makeText(requireContext(), "Please select image first", Toast.LENGTH_SHORT)
                    .show()
            } else {
                lifecycleScope.launch {
                    FirebaseStorageManager.triggerImage.collect {
                        if (it != "") {
                            saveFireStore(it)
                        }
                    }
                }
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2500)
                    findNavController().popBackStack()
                }
            }
//            onRefresh()
        }
    }
//    }

    fun saveFireStore(imgURI: String) {
        FirebaseStorageManager().saveFireStore(
            binding.eventTitle.text.toString(),
            binding.eventNote.text.toString(),
            imgURI,
            getData(),
            getEmail(),
            binding.eventLocalization.text.toString(),
            requireContext()
        )
        Log.d("imgurl", "${imgURI}")
    }

    private fun getEmail(): String {
        return args.email
    }

    private fun getData(): String {
        LocalDateTime.now()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }

    fun onRefresh() {
        var uri = Uri.parse(MainActivity.eventImage)
        binding.eventImage.setImageURI(uri)
        binding.addImage.tag = uri
    }
}