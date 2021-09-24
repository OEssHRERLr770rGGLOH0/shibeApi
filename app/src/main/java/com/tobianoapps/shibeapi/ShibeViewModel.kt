package com.tobianoapps.shibeapi

import android.app.Application
import androidx.lifecycle.*
import com.tobianoapps.shibeapi.list.api.ShibeApi
import com.tobianoapps.shibeapi.list.api.ShibeModels
import com.tobianoapps.shibeapi.list.api.ShibeModels.Shibe
import com.tobianoapps.shibeapi.list.repository.ShibeRepositoryImpl
import com.tobianoapps.shibeapi.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ShibeViewModel(
    application: Application
) : AndroidViewModel(application), KoinComponent {

    private val repository: ShibeRepositoryImpl by inject()
    private val ioDispatcher: CoroutineDispatcher by inject()

    /**
     * Response from the [ShibeApi].
     */
    private var _shibeResponse = MutableLiveData<Resource<List<String>?>>()

    private fun callShibeApi() {
        viewModelScope.launch(ioDispatcher) {
            _shibeResponse.postValue(Resource.Loading())
            _shibeResponse.postValue(repository.getShibes())
        }
    }

    fun onFabClick() {
        callShibeApi()
    }

    /**
     * Transformed [LiveData] that can be observed in Activities and Fragments.
     */
    val shibeResponse: LiveData<List<Shibe>?> = _shibeResponse.map {
        it.data?.map { url ->
            Shibe(
                url = url,
                doggoLingo = ShibeModels.DoggoLingo.randomSentence
            )
        }
    }

    /**
     * Map response state and emit
     */
    val showLoadingView: LiveData<Boolean> = _shibeResponse.map {
        when (it) {
            is Resource.Loading<*> -> true
            else -> false
        }
    }

    init {
       callShibeApi()
    }
}