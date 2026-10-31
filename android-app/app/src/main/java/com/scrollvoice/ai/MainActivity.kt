package com.scrollvoice.ai

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val bg = Color.rgb(10, 14, 29)
    private val card = Color.rgb(21, 28, 49)
    private val white = Color.rgb(245, 247, 255)
    private val muted = Color.rgb(155, 166, 190)
    private val cyan = Color.rgb(0, 220, 235)
    private val purple = Color.rgb(151, 105, 255)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(22), dp(22), dp(24))
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val logo = TextView(this).apply {
            text = "◉"
            textSize = 32f
            setTextColor(cyan)
        }

        val brand = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(10), 0, 0, 0)
        }

        brand.addView(text("ScrollVoice", 21f, white, true))
        brand.addView(text("AI VOICE CONTROL", 10f, muted, true))

        header.addView(logo)
        header.addView(brand)
        header.addView(View(this), LinearLayout.LayoutParams(0, 1, 1f))
        header.addView(text("● AKTIF", 11f, cyan, true))

        content.addView(header)
        content.addView(space(30))

        content.addView(text("Kendalikan video", 27f, white, true))
        content.addView(text("cukup dengan suara.", 27f, white, true))
        content.addView(space(8))
        content.addView(text("ScrollVoice AI siap menjadi asisten scroll kamu.", 13f, muted))
        content.addView(space(25))

        val micCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(18), dp(25), dp(18), dp(25))
            background = rounded(card, 24)
        }

        val mic = TextView(this).apply {
            text = "🎙"
            textSize = 43f
            gravity = Gravity.CENTER
            setTextColor(cyan)
            background = rounded(Color.rgb(28, 47, 77), 100)
            layoutParams = LinearLayout.LayoutParams(dp(104), dp(104))
        }

        micCard.addView(mic)
        micCard.addView(space(16))
        micCard.addView(text("MODE SUARA", 11f, cyan, true))
        micCard.addView(space(7))
        micCard.addView(text("Siap mendengarkan", 20f, white, true))
        micCard.addView(space(6))
        micCard.addView(text("Aktifkan mikrofon untuk mulai menggunakan perintah.", 12f, muted))
        content.addView(micCard)

        content.addView(space(27))
        content.addView(text("ENAM PERINTAH SUARA", 13f, white, true))
        content.addView(space(13))

        val commands = listOf(
            Triple("↓", "Scroll", "Gulir ke video berikutnya"),
            Triple("↑", "Balik", "Gulir kembali ke atas"),
            Triple("Ⅱ", "Stop", "Jeda penerimaan perintah"),
            Triple("▶", "Lanjut", "Lanjutkan penerimaan perintah"),
            Triple("↻", "Otomatis", "Mode otomatis"),
            Triple("⏻", "Matikan", "Akhiri sesi suara")
        )

        val colors = listOf(
            cyan,
            purple,
            Color.rgb(255, 174, 70),
            Color.rgb(70, 210, 150),
            Color.rgb(90, 160, 255),
            Color.rgb(255, 100, 120)
        )

        commands.forEachIndexed { index, item ->
            val commandCard = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(15), dp(13), dp(15), dp(13))
                background = rounded(card, 17)
            }

            val symbol = TextView(this).apply {
                text = item.first
                textSize = 25f
                gravity = Gravity.CENTER
                setTextColor(colors[index])
                background = rounded(Color.rgb(34, 42, 67), 14)
                layoutParams = LinearLayout.LayoutParams(dp(52), dp(52))
            }

            val labels = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp(14), 0, 0, 0)
            }

            labels.addView(text(item.second, 16f, white, true))
            labels.addView(space(4))
            labels.addView(text(item.third, 11f, muted))

            commandCard.addView(symbol)
            commandCard.addView(labels)
            content.addView(commandCard)
            content.addView(space(10))
        }

        content.addView(space(12))
        content.addView(text("ScrollVoice AI • Versi 1.0", 11f, muted).apply {
            gravity = Gravity.CENTER
        })

        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        val navigation = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(10), dp(8), dp(10))
            background = rounded(Color.rgb(17, 23, 40), 18)
        }

        listOf("⌂\nBeranda", "☷\nPerintah", "⚙\nPengaturan", "ⓘ\nTentang").forEachIndexed { i, label ->
            val item = TextView(this).apply {
                text = label
                textSize = 11f
                gravity = Gravity.CENTER
                setTextColor(if (i == 0) cyan else muted)
                setPadding(dp(5), dp(5), dp(5), dp(5))
                layoutParams = LinearLayout.LayoutParams(0, dp(52), 1f)
            }
            navigation.addView(item)
        }

        root.addView(navigation)
        setContentView(root)
    }

    private fun text(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) setTypeface(null, Typeface.BOLD)
        }
    }

    private fun space(height: Int): View {
        return View(this).apply {
            layoutParams = LinearLayout.LayoutParams(1, dp(height))
        }
    }

    private fun rounded(color: Int, radius: Int): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()
        }
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }
}
