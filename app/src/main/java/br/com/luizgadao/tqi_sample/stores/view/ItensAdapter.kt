package br.com.luizgadao.tqi_sample.stores.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.luizgadao.tqi_sample.R
import br.com.luizgadao.tqi_sample.stores.model.Local
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
        var tvValor: AppCompatTextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_titulo)
            tvNota = itemView.findViewById(R.id.tv_nota)
            tvTipo = itemView.findViewById(R.id.tv_tipo)
            tvTempo = itemView.findViewById(R.id.tv_tempo)
            tvValor = itemView.findViewById(R.id.tv_valor)
            icLogo = itemView.findViewById(R.id.ic_logo)
            icLike = itemView.findViewById(R.id.ic_like)
        }

        @SuppressLint("SetTextI18n")
        fun onBind(local: Local) {

            tvTitle?.text = local.titulo
            tvNota?.text = local.nota.toString()

            val distancia = (String.format("%.1f", local.distancia.div(1000.0)) + " Km")
                .replace(".", ",")

            val frete = if (local.frete <= 0.0)
                "Grátis"
            else
                "R$ " + String.format("%.2f", local.frete).replace(".", ",")

            tvTipo?.text = "| ${local.tipo} | $distancia"
            tvTempo?.text = "${local.tempo} | "
            tvValor?.text = frete
            tvValor?.setTextColor(
                if (frete.equals("Grátis"))
                    Color.parseColor("#4CAF50")
                else
                    Color.parseColor("#000000")
            )

            loadLog(local.url)

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

    @SuppressLint("NotifyDataSetChanged")
    fun update(itens: List<Local>) {
        this.itens = itens
        notifyDataSetChanged()
    }
}