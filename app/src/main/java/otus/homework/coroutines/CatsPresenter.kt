package otus.homework.coroutines

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatsPresenter(
    private val catsService: CatsService
) {

    private var _catsView: ICatsView? = null

    fun onInitComplete() {
        val job = launch(MainDispatcher + CoroutineName("Pet")) {
            try {
                val getcat = catsService.getCatFact()
                val responce = _catsView?.populate()

                uiState.value = UiState.Success(responce)
            } catch (ex: Exception) {
                when (ex) {
                    is SocketTimeoutException -> {
                        Toast.makeText(
                            getApplicationContext(),
                            "Failed to get responce from server",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                    else -> CrashMonitor.trackWarning() {
                        Toast.makeText(
                            getApplicationContext(),
                            exception.message,
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }
        }
        job.cancel()
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        _catsView = null
    }
}