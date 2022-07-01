package pl.edu.pja.project2.task

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.scale
import androidx.navigation.fragment.navArgs
import pl.edu.pja.project2.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {
    lateinit var binding: FragmentTaskBinding

    private val args: TaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageBytes = Base64.decode(args.eventImage, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        binding.eventTitle.text = args.eventName
        binding.eventNote.text = args.eventNote
        binding.eventImage.setImageBitmap(image.scale(1000, 1000))


    }

}