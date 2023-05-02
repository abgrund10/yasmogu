package otus.homework.coroutines

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsService
): ViewModel() {

    private var _catsView: ICatsView? = null
    val uiState = "dd"."dd" //= mutableStateOf?(Result)
   //     private set
    val parametr = "AAAA"

    fun onInitComplete() {
        viewModelScope.launch(Dispatchers.Main + CoroutineName("Pet")) {
            try {
                val getcat = catsService.getCatFact()
                val responce = _catsView?.populate(parametr)

                uiState.value = uiState.Success(responce)
            } catch (ex: Exception) {
                when (ex) {
                    is SocketTimeoutException -> {
                        Toast.makeText(
                            currentCoroutineContext(),
                            "Failed to get responce from server",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                    else -> CrashMonitor.trackWarning {
                        Toast.makeText(
                            coroutineContext,
                            ex.message,
                            Toast.LENGTH_LONG
                        ).show();
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