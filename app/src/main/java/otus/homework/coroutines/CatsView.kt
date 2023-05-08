package otus.homework.coroutines

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlin.Result

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    var presenter :CatsPresenter? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<Button>(R.id.button).setOnClickListener {
            presenter?.onInitComplete()
        }
    }

    fun populate(model: String) {
        findViewById<TextView>(R.id.fact_textView).text = model.toString()
        Picasso.get()
            .load(model.toString())
            .into(findViewById<ImageView>(R.id.imageV))
    }

    override fun toastDisplay(text: String) {
        findViewById<TextView>(R.id.fact_textView).text = text
    }
}

interface ICatsView {

    fun populate(model: Result.Companion)
    fun toastDisplay(text: String)
}