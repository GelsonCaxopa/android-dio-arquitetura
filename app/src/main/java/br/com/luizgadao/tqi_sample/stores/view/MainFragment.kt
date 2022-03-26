package br.com.luizgadao.tqi_sample.stores.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.luizgadao.tqi_sample.R
import br.com.luizgadao.tqi_sample.stores.StoresContract
import br.com.luizgadao.tqi_sample.stores.model.RepositoryImpl
import br.com.luizgadao.tqi_sample.stores.presenter.PresenterImpl
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse

class MainFragment : Fragment(R.layout.fragment_main), StoresContract.View {

    private val presenter by lazy {
        val scope = viewLifecycleOwner.lifecycleScope
        val repository = RepositoryImpl(scope = scope)
        PresenterImpl(view = this, repository = repository, scope)
    }

    private lateinit var itensAdapter: ItensAdapter
    private var recyclerView: RecyclerView? = null
    private var swipe: SwipeRefreshLayout? = null
    private var emptyView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView(view)
        presenter.initialize()
        presenter.load()
    }

    private fun initializeView(view: View) {
        recyclerView = view.findViewById(R.id.rv)
        swipe = view.findViewById(R.id.swipe)
        emptyView = view.findViewById(R.id.emptyView)

        swipe?.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primaryColor),
            ContextCompat.getColor(requireContext(), R.color.secondaryColor),
            ContextCompat.getColor(requireContext(), R.color.primaryDarkColor),
            ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
        )
    }

    override fun setupUI() {
        itensAdapter = ItensAdapter(
            itemClickListener = {
                Toast.makeText(requireContext(), "click - item: ${it.titulo}", Toast.LENGTH_SHORT)
                    .show()
            },
            likeClickListener = {
                Toast.makeText(requireContext(), "click - like: ${it.frete}", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        recyclerView?.adapter = itensAdapter
        swipe?.setOnRefreshListener { presenter.load() }
    }

    override fun showLoading() {
        swipe?.isRefreshing = true
    }

    override fun hideLoading() {
        swipe?.isRefreshing = false
    }

    override fun updateUI(jsonResponse: JsonResponse?) {
        if (jsonResponse != null) {
            if (jsonResponse.payload.locais.isEmpty()) {
                recyclerView?.visibility = View.GONE
                emptyView?.visibility = View.VISIBLE
            } else {
                itensAdapter.update(jsonResponse.payload.locais)
                emptyView?.visibility = View.GONE
                recyclerView?.visibility = View.VISIBLE
            }
        }
    }
}