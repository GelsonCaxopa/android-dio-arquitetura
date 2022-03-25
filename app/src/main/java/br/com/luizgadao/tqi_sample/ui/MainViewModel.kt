package br.com.luizgadao.tqi_sample.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luizgadao.tqi_sample.data.RestauranteRepository
import br.com.luizgadao.tqi_sample.data.RestauranteRepositoryEmMemoria
import br.com.luizgadao.tqi_sample.data.RestauranteRepositoryOkHttp
import br.com.luizgadao.tqi_sample.ui.data.JsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val repository: RestauranteRepository by lazy {
        RestauranteRepositoryOkHttp(viewModelScope)
    }

    private val response: MutableLiveData<JsonResponse?> by lazy {
        MutableLiveData<JsonResponse?>().also {
            this.carregar()
        }
    }

    fun getRestaurantes(): LiveData<JsonResponse?> {
        return response
    }

    private fun carregar() {
        viewModelScope.launch {
            var jsonResponse: JsonResponse? = null
            jsonResponse = withContext(Dispatchers.IO) {
                repository.getRestaurantes()
            }

            response.value = jsonResponse
        }
    }

}