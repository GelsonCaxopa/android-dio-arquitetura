package br.com.luizgadao.tqi_sample.stores

import br.com.luizgadao.tqi_sample.ui.data.JsonResponse

interface StoresContract {

    interface View {
        fun setupUI()
        fun showLoading()
        fun hideLoading()
        fun updateUI(jsonResponse: JsonResponse?)
    }

    interface Presenter {
        fun initialize()
        fun load()
    }

}