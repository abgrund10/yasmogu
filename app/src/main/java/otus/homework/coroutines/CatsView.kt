package otus.homework.coroutines

import android.content.Context
import android.provider.ContactsContract
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.app.R
import com.squareup.picasso.Picasso

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

    override fun populate(fact: Additional) {
        findViewById<TextView>(R.id.fact_textView).text = fact.fact
        Picasso.get()
            .load(fact.url)
            .into(findViewById<ImageView>(R.id.imageV))
    }

    override fun toastDisplay(text: String) {
        findViewById<TextView>(R.id.fact_textView).text = text
    }
}

interface ICatsView {

    fun populate(fact: Additional)
    fun toastDisplay(text: String)
}