package me.guilherme.s.forato.androidApp.Adapter

import PostResponse.Week
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.guilherme.s.forato.androidApp.databinding.ItemPrevisionBinding
import me.guilherme.s.forato.androidApp.util.Util

@ExperimentalCoroutinesApi
class Horizontal_Recycler(
    var listaw: List<Week>,
) : RecyclerView.Adapter<Horizontal_Recycler.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemPrevisionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(listaw[position])
    }

    override fun getItemCount(): Int = listaw.count()

    @ExperimentalCoroutinesApi
    class MyViewHolder(private val binding: ItemPrevisionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bindView(func: Week) {
            binding.imgClima.setImageResource(Util().setImage(func.weather[0].description))
            binding.txtDescription.text = func.weather[0].description
            binding.txtMax.text = "${Util().convertCelsius(func.temp.max)}°C"
            binding.txtMin.text = "${Util().convertCelsius(func.temp.min)}°C"
            binding.txtCodigo.text = Util().convertDay(func.dt.toLong())
        }
    }

    fun getList(lista: List<Week>) {
        listaw = lista
    }
}