package br.com.luizgadao.tqi_sample.ui.data

import kotlinx.serialization.Serializable

@Serializable
data class JsonResponse(
    val payload: Payload
)

@Serializable
data class Payload(
    val locais: List<Local>
)

@Serializable
data class Local(
    val distancia: Int,
    val extra: Extra,
    val frete: Double,
    val nota: Double,
    val tempo: String,
    val tipo: String,
    val titulo: String,
    val url: String
)

@Serializable
data class Extra(
    val descricao: String,
    val type: String
)

val json = """
    {
        "payload":{
           "locais":[
              {
                 "titulo":"Flor de Sal",
                 "url":"http://www.img.com/img",
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
                 "url":"http://www.img.com/img",
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