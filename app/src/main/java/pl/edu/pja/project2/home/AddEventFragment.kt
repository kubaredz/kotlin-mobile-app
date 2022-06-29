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
import androidx.navigation.fragment.navArgs
import com.github.dhaval2404.imagepicker.ImagePicker
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
                .maxResultSize(1080, 1080)
                .start()

        }
        binding.addEvent.setOnClickListener {
            onRefresh()
            val imgURI = binding.addEvent.tag as Uri?
            if (imgURI == null) {
                Toast.makeText(requireContext(), "Please select image first", Toast.LENGTH_SHORT)
                    .show()
            } else {
                FirebaseStorageManager().uploadImage(requireContext(), imgURI)
                FirebaseStorageManager().saveFireStore(binding.eventTitle.toString(), binding.eventNote.toString(), imgURI.toString(), getData(), getEmail(), requireContext())
            }

        }
    }

    private fun getEmail(): String {
        return args.email
    }

    private fun getData(): String {
        LocalDateTime.now()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }

    fun onRefresh(){
        var uri = Uri.parse(MainActivity.eventImage)
        binding.eventImage.setImageURI(uri)
            binding.addImage.tag = uri
    }
}