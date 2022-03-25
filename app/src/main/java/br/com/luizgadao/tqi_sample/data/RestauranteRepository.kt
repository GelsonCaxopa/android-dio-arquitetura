package br.com.luizgadao.tqi_sample.data

import br.com.luizgadao.tqi_sample.ui.data.JsonResponse

interface RestauranteRepository {
    suspend fun getRestaurantes() : JsonResponse?
}