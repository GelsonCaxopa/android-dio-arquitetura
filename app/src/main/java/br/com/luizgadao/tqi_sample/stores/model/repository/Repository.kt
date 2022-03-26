package br.com.luizgadao.tqi_sample.stores.model.repository

import br.com.luizgadao.tqi_sample.stores.model.JsonResponse

interface Repository {
    suspend fun getData(): JsonResponse?
}
