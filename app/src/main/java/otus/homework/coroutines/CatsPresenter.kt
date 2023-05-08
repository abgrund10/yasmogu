package otus.homework.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsServiceFact: CatsServiceFact,
    private val catsServiceImage: CatsServiceImage
): ViewModel() {

    private var _catsView: ICatsView? = null

    private val CoroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        CrashMonitor.trackWarning { t.message }
        _catsView?.toastDisplay(t.message ?: "")
    }


    fun onInitComplete() {
        viewModelScope.launch(Dispatchers.Main + CoroutineName("Pet")) {
            try {
                val url = catsServiceImage.getCatImage().first()
                val fact = catsServiceFact.getCatFact()
                _catsView?.populate(Result)

            } catch (ex: Exception) {
                when (ex) {
                    is SocketTimeoutException -> {
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
}