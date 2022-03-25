package br.com.luizgadao.tqi_sample.data

import android.util.Log
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class RestauranteRepositoryEmMemoria(
    val scope: CoroutineScope
) : RestauranteRepository {

    override suspend fun getRestaurantes(): JsonResponse? {
        var json: JsonResponse? = null
        val job = scope.launch {
            try {
                json = Json.decodeFromString(jsonString)
            } catch (e: Exception) {
                Log.e("RestauranteRepository", e.printStackTrace().toString())
            }
        }
        job.join()

        return json
    }

    val jsonString = """
    {
       "payload":{
          "locais":[
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":2.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":3.7,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":18000,
                "tempo":"39-49 min",
                "frete":5.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             },
             {
                "titulo":"Flor de Sal",
                "url":"https://user-images.githubusercontent.com/3384999/159463167-698b42c9-3757-4886-b205-1c0ad9018d1b.png",
                "nota":4.5,
                "tipo":"Conteporânea",
                "distancia":1800,
                "tempo":"39-49 min",
                "frete":0.0,
                "extra":{
                   "type":"CUPOM",
                   "descricao":"Cupom de R${'$'} 12 disponível"
                }
             }
          ]
       }
    }
    """.trimIndent()
}