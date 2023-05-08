package otus.homework.coroutines

import android.view.Display.Mode
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CatsViewModel(
    private val catsServiceFact: CatsServiceFact,
    private val catsServiceImage: CatsServiceImage
): ViewModel() {

    private var _catsView: ICatsView? = null

    private val CoroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        CrashMonitor.trackWarning { t.message }
        _catsView?.toastDisplay(t.message ?: "")
    }
    val resultLiveData = MutableLiveData<Result>()


    fun onInitComplete() {
        viewModelScope.launch(Dispatchers.Main + CoroutineName("Pet")) {
            try {
                val url = catsServiceImage.getCatImage().first()
                val fact = catsServiceFact.getCatFact()
                _catsView?.populate(Result)

                resultLiveData.value = Result.Error()

            } catch (ex: Exception) {
                when (ex) {
                    is SocketTimeoutException -> {
                        _catsView?.toastDisplay("Failed to get responce from server")

                    }

                    else -> CrashMonitor.trackWarning {
                        CoroutineExceptionHandler.toString()
                    }
                }
            }
        }
        viewModelScope.cancel()
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }

    class CatsViewModelFactory(
        private val catsServiceFact: CatsServiceFact,
        private val catsServiceImage: CatsServiceImage
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CatsViewModel(catsServiceFact, catsServiceImage) as T
        }
    }
}