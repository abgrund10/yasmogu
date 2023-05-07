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
    private val catsService: CatsService
): ViewModel() {

    private var _catsView: ICatsView? = null

  //  val coroutineExceptionHandler = CoroutineExceptionHandler{_, t ->
   //         t.message
    //    _catsView?.toastDisplay(t.message ?: "")
   // }
    private val CoroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        CrashMonitor.trackWarning { t.message }
        _catsView?.toastDisplay(t.message ?: "")
    }


    fun onInitComplete() {
        viewModelScope.launch(Dispatchers.Main + CoroutineName("Pet")) {
            try {
                val getcat = catsService.getCatFact()
                val url = "https://aws.random.cat/meow"
                val fact = catsService.getCatFact()
                _catsView?.populate(Additional(fact.text, url))

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
}

//   fun showToast(text: String) {
//        Toast.makeText(App.INSTANCE?.applicationContext, text, Toast.LENGTH_LONG).show()
//showToast(e.message.toString())