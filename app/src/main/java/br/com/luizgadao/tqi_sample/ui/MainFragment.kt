package br.com.luizgadao.tqi_sample.ui

import android.os.Bundle
import android.util.Log
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
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse


class MainFragment : Fragment(), ListContract.View {

    private lateinit var itensAdapter: ItensAdapter
    private var recyclerView: RecyclerView? = null
    private var swipe: SwipeRefreshLayout? = null
    private var emptyView: View? = null

    private val presenter: ListContract.Presenter by lazy {
        Presenter(
            view = this@MainFragment,
            scope = viewLifecycleOwner.lifecycleScope
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingView(view)
        presenter.initialize()
    }

    private fun settingView(view: View) {
        recyclerView = view.findViewById(R.id.rv)
        swipe = view.findViewById(R.id.swipe)
        emptyView = view.findViewById(R.id.emptyView)
    }

    override fun setupUI() {
        setupAdapter()
        setupSwipe()
    }

    private fun setupAdapter() {
        itensAdapter = ItensAdapter(itemClickListener = {
            Toast.makeText(requireContext(), "click - item: ${it.titulo}", Toast.LENGTH_SHORT)
                .show()
        }, likeClickListener = {
            Toast.makeText(requireContext(), "click - like: ${it.frete}", Toast.LENGTH_SHORT).show()
        })
        recyclerView?.adapter = itensAdapter
    }

    private fun setupSwipe() {
        swipe?.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primaryColor),
            ContextCompat.getColor(requireContext(), R.color.secondaryColor),
            ContextCompat.getColor(requireContext(), R.color.primaryDarkColor),
            ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
        )

        swipe?.setOnRefreshListener { presenter.load() }
    }

    override fun updateUI(jsonResponse: JsonResponse?) {
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
    }

    override fun showLoading() {
        swipe?.isRefreshing = true
    }

    override fun hideLoading() {
        swipe?.isRefreshing = false
    }
}
