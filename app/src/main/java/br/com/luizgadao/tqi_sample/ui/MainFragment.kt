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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.luizgadao.tqi_sample.R
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request


class MainFragment : Fragment() {

    private lateinit var itensAdapter: ItensAdapter
    private var recyclerView: RecyclerView? = null
    private var swipe: SwipeRefreshLayout? = null
    private var emptyView: View? = null

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
                getJsonResponse()
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

    fun getJsonResponse(): JsonResponse? {
        var json: JsonResponse? = null
        val url = "https://gist.githubusercontent.com/LuizGadao/5793719642bbbbd4fae17629d0cd0266/raw/741b6bf8256aba7bc40a459703cb80a36e21b92d/payload.json"
        val client = OkHttpClient()
        val call = client.newCall(
            Request.Builder()
                .url(url)
                .build()
        )

        try {
            val response = call.execute()
            response.body?.let {
                json = Json.decodeFromString(it.string())
            }
        } catch (e:Exception) {
            Log.e("MainFragment", e.printStackTrace().toString())
        }

        return json
    }

}