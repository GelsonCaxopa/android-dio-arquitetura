package br.com.luizgadao.tqi_sample.stores.model

import br.com.luizgadao.tqi_sample.ui.data.JsonResponse

interface Repository {
    suspend fun getData(): JsonResponse?
}