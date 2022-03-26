package br.com.luizgadao.tqi_sample.stores.presenter

import androidx.lifecycle.LifecycleCoroutineScope
import br.com.luizgadao.tqi_sample.stores.StoresContract
import br.com.luizgadao.tqi_sample.stores.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresenterImpl(
    private val view: StoresContract.View?,
    private val repository: Repository,
    private val scope: LifecycleCoroutineScope
): StoresContract.Presenter {

    override fun initialize() {
        view?.setupUI()
    }

    override fun load() {
        scope.launch(Dispatchers.Main) {
            view?.showLoading()
            val response = repository.getData()
            view?.updateUI(response)
            view?.hideLoading()
        }
    }

}