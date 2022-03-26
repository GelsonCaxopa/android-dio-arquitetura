package br.com.luizgadao.tqi_sample.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Presenter(
    private val view: ListContract.View?,
    private val repository: Repository,
    private val scope: CoroutineScope
) :
    ListContract.Presenter {

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
