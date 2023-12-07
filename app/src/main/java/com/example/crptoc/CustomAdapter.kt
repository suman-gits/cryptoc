package com.example.crptoc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private var dataSet: ArrayList<model>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       val symbol: TextView
        val price: TextView
        val name: TextView

        init {
            // Define click listener for the ViewHolder's View
            symbol = view.findViewById(R.id.symbol)
            price = view.findViewById(R.id.price)
            name = view.findViewById(R.id.name)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_items, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
         setAnimation(viewHolder.itemView)
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.symbol.text=dataSet[position].symbol
        viewHolder.name.text=dataSet[position].name
        viewHolder.price.text=dataSet[position].price
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



    fun setAnimation(view: View){         //Animation
        val anim=AlphaAnimation(0.0f,1.0f)
        anim.duration=1000
        view.startAnimation(anim)



    }
    fun updateList(newlist: List<model>){        //for searchbar
        dataSet= newlist as ArrayList<model>
        notifyDataSetChanged()

    }

}
