package com.example.pagdrawable

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ImageSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.libpag.PAGImageView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val pagImageView by lazy { findViewById<PAGImageView>(R.id.pagImageView) }
    private val textView by lazy { findViewById<TextView>(R.id.textView) }
    private val button by lazy { findViewById<Button>(R.id.button) }

    private val text = "This is a very long text that should be <s> wrapped to multiple lines. And some spans should <s> be displayed here. \nClick refresh button if not working."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val path = "http://devcstatic.bottletechup.com/decorate-files/2025-08-07/m3nzai11jwmg.pag"
        pagImageView.setPathAsync(path){
            println("=====$it")
//            pagImageView.composition = it
            pagImageView.setRepeatCount(-1)
            pagImageView.play()
        }


        val spannableString = SpannableString(text)
        var end = 0
        do {
            val start = spannableString.indexOf("<s>", end)
            end = start + 3
            if (start >= 0) {
                val span = PAGSpan(this , 60f , 60f) {
                    textView.invalidate()
                }
                span.path = path
                spannableString.setSpan(span, start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        } while (start >= 0)

        textView.text = spannableString

        button.setOnClickListener {
            textView.invalidate()
        }
    }
}