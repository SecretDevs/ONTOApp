package com.example.onto.materials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R
import com.example.onto.vo.OntoArticle

class MaterialAdapter(private val materials: List<OntoArticle>) :
    RecyclerView.Adapter<MaterialAdapter.MyViewHolder>() {

    class MyViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cv: CardView = itemView.findViewById(R.id.material_card) as CardView
        val description: TextView = itemView.findViewById(R.id.material_description) as TextView
        val img: ImageView = itemView.findViewById(R.id.material_image) as ImageView //Как преобразовать тип

    }



    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MaterialAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.material_item, parent, false)

        return MyViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.description.text = materials.get(position).text
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = materials.size
}
