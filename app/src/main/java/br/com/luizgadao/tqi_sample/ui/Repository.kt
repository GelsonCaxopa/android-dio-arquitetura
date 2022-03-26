package br.com.luizgadao.tqi_sample.ui

import br.com.luizgadao.tqi_sample.ui.data.JsonResponse

interface Repository {
    suspend fun getData(): JsonResponse?
}
