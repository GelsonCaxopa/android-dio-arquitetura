package br.com.luizgadao.tqi_sample.ui

import android.util.Log
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val url =
    "https://gist.githubusercontent.com/LuizGadao/5793719642bbbbd4fae17629d0cd0266/raw/c71fc86d04213bd4af6c4d6cd3fef79e07dc9dfe/payload.json"

class Presenter(private val view: ListContract.View?, private val scope: CoroutineScope) :
    ListContract.Presenter {

    override fun initialize() {
        view?.setupUI()
        load()
    }

    override fun load() {
        scope.launch(Dispatchers.Main) {
            view?.showLoading()

            val client = OkHttpClient()
            val call = client.newCall(
                Request.Builder()
                    .url(url)
                    .build()
            )

            try {
                val response: Response = withContext(Dispatchers.IO) { call.execute() }
                response.body?.let {
                    val json: JsonResponse? = Json.decodeFromString(it.string())
                    view?.updateUI(json)
                }
            } catch (e: Exception) {
                Log.e("Presenter", e.printStackTrace().toString())
            }

            view?.hideLoading()
        }
    }

}
