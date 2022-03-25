package br.com.luizgadao.tqi_sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.luizgadao.tqi_sample.R
import br.com.luizgadao.tqi_sample.data.RestauranteRepository
import br.com.luizgadao.tqi_sample.data.RestauranteRepositoryEmMemoria
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment() {

    private lateinit var itensAdapter: ItensAdapter
    private var recyclerView: RecyclerView? = null
    private var swipe: SwipeRefreshLayout? = null
    private var emptyView: View? = null

    private val repository: RestauranteRepository by lazy {
        RestauranteRepositoryEmMemoria(scope = viewLifecycleOwner.lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

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

        swipe?.isRefreshing = true
        swipe?.setOnRefreshListener {
            getData()
        }

        getData()
    }

    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val jsonResponse: JsonResponse? = withContext(Dispatchers.IO) {
                repository.getRestaurantes()
            }

            jsonResponse?.let { res ->
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