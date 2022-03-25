package br.com.luizgadao.tqi_sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.luizgadao.tqi_sample.R


class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var itensAdapter: ItensAdapter
    private var recyclerView: RecyclerView? = null
    private var swipe: SwipeRefreshLayout? = null
    private var emptyView: View? = null

    val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv)
        swipe = view.findViewById(R.id.swipe)
        emptyView = view.findViewById(R.id.emptyView)

        itensAdapter = ItensAdapter(
            itemClickListener = {
                Toast.makeText(requireContext(), "click - item: ${it.titulo}", Toast.LENGTH_SHORT).show()
            },
            likeClickListener = {
                Toast.makeText(requireContext(), "click - like: ${it.frete}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView?.adapter = itensAdapter

        swipe?.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primaryColor),
            ContextCompat.getColor(requireContext(), R.color.secondaryColor),
            ContextCompat.getColor(requireContext(), R.color.primaryDarkColor),
            ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
        )

        swipe?.setOnRefreshListener {
            getData()
        }

        getData()
    }

    private fun getData() {
        swipe?.isRefreshing = true
        viewModel
            .getRestaurantes()
            .observe(viewLifecycleOwner)
            { response ->
                response?.let { res ->
                    if (res.payload.locais.isEmpty()) {
                        recyclerView?.visibility = View.GONE
                        emptyView?.visibility = View.VISIBLE
                    } else {
                        itensAdapter.update(res.payload.locais)
                        emptyView?.visibility = View.GONE
                        recyclerView?.visibility = View.VISIBLE
                    }
                }

                swipe?.isRefreshing = false
            }
    }
}