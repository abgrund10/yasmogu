package otus.homework.coroutines

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    lateinit var catsPresenter: CatsPresenter
    private val diContainer = DiContainer()
    private val CatsViewModel: CatsViewModel by viewModels {
        otus.homework.coroutines.CatsViewModel.CatsViewModelFactory(
            diContainer.factService,
            diContainer.imageService
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsPresenter = CatsPresenter(diContainer.factService, diContainer.imageService)
        view.presenter = catsPresenter
        catsPresenter.attachView(view)

        CatsViewModel.resultLiveData.observe(this)  { Result ->
            when (Result) {
                is Result -> {
                    view.populate(Result.toString())
                }

                else -> {
                    view.toastDisplay("Failed to get responce from server")
                }
            }
        }
        findViewById<Button>(R.id.button).setOnClickListener {
            CatsViewModel.onInitComplete()
        }
            catsPresenter.onInitComplete()
    }

    override fun onStop() {
        if (isFinishing) {
            catsPresenter.detachView()
        }
        super.onStop()
    }
}