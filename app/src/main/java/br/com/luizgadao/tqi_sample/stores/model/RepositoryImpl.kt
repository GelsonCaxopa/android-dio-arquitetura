package br.com.luizgadao.tqi_sample.stores.model

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

private val URL =
    "https://gist.githubusercontent.com/LuizGadao/5793719642bbbbd4fae17629d0cd0266/raw/741b6bf8256aba7bc40a459703cb80a36e21b92d/payload.json"

class RepositoryImpl(private val scope: LifecycleCoroutineScope) : Repository {

    override suspend fun getData(): JsonResponse? = try {
        withContext(scope.coroutineContext + Dispatchers.IO) {
            val response = getCall().execute()
            response.body?.let { Json.decodeFromString(it.string()) }
        }
    } catch (e: Exception) {
        Log.e(javaClass.name, e.printStackTrace().toString())
        null
    }

    private fun getCall(): Call {
        val client = OkHttpClient()
        return client.newCall(
            Request.Builder()
                .url(URL)
                .build()
        )
    }

}