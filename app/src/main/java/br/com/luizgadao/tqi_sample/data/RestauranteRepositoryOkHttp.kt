package br.com.luizgadao.tqi_sample.data

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

const val url =
    "https://gist.githubusercontent.com/LuizGadao/5793719642bbbbd4fae17629d0cd0266/raw/741b6bf8256aba7bc40a459703cb80a36e21b92d/payload.json"

class RestauranteRepositoryOkHttp(
    val scope: CoroutineScope
) : RestauranteRepository {

    override suspend fun getRestaurantes(): JsonResponse? {
        var json: JsonResponse? = null
        val call = createCall()

        val job = scope.launch {
            try {
                val response = withContext(Dispatchers.IO) { call.execute() }
                response.body?.let {
                    json = Json.decodeFromString(it.string())
                }
            } catch (e: Exception) {
                Log.e("RestauranteRepository", e.printStackTrace().toString())
            }
        }
        job.join()

        return json
    }

    private fun createCall(): Call {
        val client = OkHttpClient()
        val call = client.newCall(
            Request.Builder()
                .url(url)
                .build()
        )
        return call
    }
}