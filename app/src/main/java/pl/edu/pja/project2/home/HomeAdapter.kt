package pl.edu.pja.project2.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import pl.edu.pja.project2.R
import java.io.ByteArrayOutputStream

class HomeAdapter (
    private val eventList: ArrayList<EventModel>,
    private val onItemSave:
        (String, String, ImageView, String) -> Unit,
    private val onItemClicked: (String, String, Bitmap) -> Unit,
    private val onItemLongClick: (Int, String) -> Unit,
    ): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    val storage = FirebaseStorage.getInstance()
    var items = arrayListOf<ArrayList<String>>(arrayListOf())

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameEvent = itemView.findViewById<TextView>(R.id.title)
        val location = itemView.findViewById<TextView>(R.id.localisation)
        val image = itemView.findViewById<ImageView>(R.id.eventImage)
        val date = itemView.findViewById<TextView>(R.id.date)
        val root = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_layout,parent, false)
        return HomeViewHolder(itemView)
    }

    // Dodanie zawartosci elementow na liscie zdarzeń
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
//        val bitmap = BitmapFactory.decodeFile(items[position][3])
//        val uploadTask = mStorageRef.child("posts/").getFile(Uri.parse(items[position][3])).onSuccessTask {
//            it.task.
        val httpsReference = storage.getReferenceFromUrl(items[position][3])
        val ONE_MEGABYTE: Long = 1024 * 1024

        httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val itBitmap = byteArrayToBitmap(it)

            if (items.isNotEmpty()) {
                if (items[position].isNotEmpty()) {
//            Log.d("value", "Adapter: ${items[position]}")
                    holder.nameEvent.text = items[position][1]
                    holder.location.text = items[position][5]
                    holder.image.setImageBitmap(itBitmap)
                    Log.d("event123", items[position][3])
                    holder.date.text = items[position][4]
                }
            }
            holder.root.setOnClickListener {
                onItemClicked(items[position][1], items[position][5], itBitmap)
        }

            holder.root.setOnClickListener {
                onItemLongClick(position, items[position][6])
            }
        }




        // TODO
        // onItemClicked

        //    document.data.getValue("author").toString(),
//    document.data.getValue("eventTitle").toString(),
//    document.data.getValue("description").toString(),
//    document.data.getValue("imagePath").toString(),
//    document.data.getValue("data").toString()

//        onItemSave(event.nameEvent, event.location, event.image, event.date)
    }



    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
