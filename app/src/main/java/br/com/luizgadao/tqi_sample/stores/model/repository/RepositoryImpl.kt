package br.com.luizgadao.tqi_sample.stores.model.repository

import android.util.Log
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

private const val url =
    "https://gist.githubusercontent.com/LuizGadao/5793719642bbbbd4fae17629d0cd0266/raw/c71fc86d04213bd4af6c4d6cd3fef79e07dc9dfe/payload.json"

class RepositoryImpl(private val scope: CoroutineScope) : Repository {

    override suspend fun getData(): JsonResponse? = try {
        withContext(scope.coroutineContext + Dispatchers.IO) {
            val result: Response = getCall().execute()
            result.body?.let { Json.decodeFromString(it.string()) }
        }
    } catch (e: Exception) {
        Log.e(this.javaClass.name, e.printStackTrace().toString())
        null
    }

    private fun getCall(): Call {
        val client = OkHttpClient()
        return client.newCall(Request.Builder().url(url).build())
    }
}
