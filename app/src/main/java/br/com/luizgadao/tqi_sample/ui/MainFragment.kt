package br.com.luizgadao.tqi_sample.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import br.com.luizgadao.tqi_sample.R
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request


class MainFragment : Fragment() {

    private var myTv: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myTv = view.findViewById(R.id.my_tv)

        viewLifecycleOwner.lifecycleScope.launch {

            val jsonResponse:JsonResponse? = withContext(Dispatchers.IO) {
                getJsonResponse()
            }

            jsonResponse.let {  res ->
                myTv?.text = res.toString()
            }
        }
    }

    fun getJsonResponse(): JsonResponse? {
        var json: JsonResponse? = null
        val url = "https://gist.githubusercontent.com/LuizGadao/5793719642bbbbd4fae17629d0cd0266/raw/476a11c32e5f1eb93e7aec7f5034b860398803c6/gistfile1.txt"
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