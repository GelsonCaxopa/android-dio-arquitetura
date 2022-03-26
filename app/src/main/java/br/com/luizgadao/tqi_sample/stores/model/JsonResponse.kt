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
