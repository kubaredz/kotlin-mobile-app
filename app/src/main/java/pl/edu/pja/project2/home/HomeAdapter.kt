package pl.edu.pja.project2.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.project2.R

class HomeAdapter (
    private val eventList: ArrayList<EventModel>,
    private val onItemSave:
        (String, String, ImageView, String) -> Unit,
    private val onItemClicked: () -> Unit
    ): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var items = arrayListOf<ArrayList<String>>(arrayListOf())

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameEvent = itemView.findViewById<TextView>(R.id.title)
        val location = itemView.findViewById<TextView>(R.id.localisation)
        val image = itemView.findViewById<ImageView>(R.id.eventImage)
        val date = itemView.findViewById<TextView>(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_layout,parent, false)
        return HomeViewHolder(itemView)
    }

    // TODO
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        // TODO
        if(items[position].isNotEmpty()) {
//            Log.d("value", "Adapter: ${items[position]}")
            holder.nameEvent.text = items[position][1]
            holder.location.text = items[position][5]
//        holder.image.setImageDrawable(items[position][3])
            holder.date.text = items[position][4]
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

    override fun getItemCount(): Int {
        return items.size
    }
}
