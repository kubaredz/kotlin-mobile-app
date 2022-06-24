package pl.edu.pja.project2.home

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
        (String, String, ImageView, String) -> Unit
    ): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


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
        val event = eventList[position]
        holder.nameEvent.text = event.nameEvent
        holder.location.text = event.nameEvent
        holder.image.setImageDrawable(event.image.drawable)
        holder.date.text = event.nameEvent

        onItemSave(event.nameEvent, event.location, event.image, event.date)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}
