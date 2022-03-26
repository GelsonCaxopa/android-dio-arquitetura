package br.com.luizgadao.tqi_sample.stores.presenter

import br.com.luizgadao.tqi_sample.stores.StoresContract
import br.com.luizgadao.tqi_sample.stores.model.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Presenter(
    private val view: StoresContract.View?,
    private val repository: Repository,
    private val scope: CoroutineScope
) :
    StoresContract.Presenter {

    override fun initialize() {
        view?.setupUI()
        load()
    }

    override fun load() {
        scope.launch(Dispatchers.Main) {
            view?.showLoading()
            view?.updateUI(repository.getData())
            view?.hideLoading()
        }
    }

}
