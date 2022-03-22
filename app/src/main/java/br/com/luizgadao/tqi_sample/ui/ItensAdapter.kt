package br.com.luizgadao.tqi_sample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.luizgadao.tqi_sample.R
import br.com.luizgadao.tqi_sample.ui.data.Local
import coil.load
import coil.transform.CircleCropTransformation

typealias ItemClickListener = (Local) -> Unit
typealias LikeClickListener = (Local) -> Unit

class ItensAdapter(
    private val itemClickListener: ItemClickListener,
    private val likeClickListener: LikeClickListener,
) : RecyclerView.Adapter<ItensAdapter.ItemViewHolder>() {

    private var itens: List<Local> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_restaurante, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(itens[position])
    }

    override fun getItemCount() = itens.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: AppCompatTextView? = null
        var tvNota: AppCompatTextView? = null
        var tvTipo: AppCompatTextView? = null
        var tvTempo: AppCompatTextView? = null
        var icLogo: AppCompatImageView? = null
        var icLike: AppCompatImageView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_titulo)
            tvNota = itemView.findViewById(R.id.tv_nota)
            tvTipo = itemView.findViewById(R.id.tv_tipo)
            tvTempo = itemView.findViewById(R.id.tv_tempo)
            icLogo = itemView.findViewById(R.id.ic_logo)
            icLike = itemView.findViewById(R.id.ic_like)
        }

        fun onBind(local: Local) {
            local.run {
                tvTitle?.text = this.titulo
                tvNota?.text = this.nota.toString()
                tvTipo?.text = ". ${this.tipo} . ${this.distancia}"
                tvTempo?.text = "${this.tempo} . ${this.frete}"

                loadLog(this.url)
            }

            itemView.setOnClickListener { itemClickListener(local) }
            icLike?.setOnClickListener { likeClickListener(local) }
        }

        fun loadLog(url: String) {
            icLogo?.load(url) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    fun update(itens: List<Local>) {
        this.itens = itens
        notifyDataSetChanged()
    }
}