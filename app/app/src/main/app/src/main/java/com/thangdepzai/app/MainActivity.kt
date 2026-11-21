package com.thangdepzai.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private val prefs by lazy {
        getSharedPreferences("THANG_DEP_ZAI", MODE_PRIVATE)
    }

    private val switches = mutableListOf<Switch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        giaoDien()
    }

    private fun giaoDien() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(25, 25, 25, 25)
        layout.setBackgroundColor(Color.rgb(15, 15, 25))

        val scroll = ScrollView(this)
        scroll.addView(layout)

        val title = TextView(this)
        title.text = "THANG DEP ZAI"
        title.textSize = 28f
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.RED)
        layout.addView(title)

        val subtitle = TextView(this)
        subtitle.text = "BẢNG ĐIỀU KHIỂN TỐI ƯU GAME"
        subtitle.gravity = Gravity.CENTER
        subtitle.setTextColor(Color.WHITE)
        subtitle.setPadding(0, 15, 0, 25)
        layout.addView(subtitle)

        val functions = listOf(
            "Chế độ chơi game",
            "Giảm hiệu ứng giao diện",
            "Nhắc đóng ứng dụng không dùng",
            "Giữ màn hình sáng"
        )

        functions.forEachIndexed { index, name ->

            val sw = Switch(this)
            sw.text = name
            sw.textSize = 16f
            sw.setTextColor(Color.WHITE)

            sw.isChecked = prefs.getBoolean("switch_$index", false)

            sw.setOnCheckedChangeListener { _, checked ->
                prefs.edit()
                    .putBoolean("switch_$index", checked)
                    .apply()

                if (index == 3) {
                    if (checked) {
                        window.addFlags(
                            android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        )
                    } else {
                        window.clearFlags(
                            android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        )
                    }
                }
            }

            switches.add(sw)
            layout.addView(sw)
        }

        val on = Button(this)
        on.text = "BẬT TẤT CẢ"
        on.setOnClickListener {
            switches.forEach { it.isChecked = true }
        }
        layout.addView(on)

        val off = Button(this)
        off.text = "TẮT TẤT CẢ"
        off.setOnClickListener {
            switches.forEach { it.isChecked = false }
        }
        layout.addView(off)

        val reset = Button(this)
        reset.text = "KHÔI PHỤC MẶC ĐỊNH"
        reset.setOnClickListener {
            prefs.edit().clear().apply()
            switches.forEach { it.isChecked = false }
        }
        layout.addView(reset)

        setContentView(scroll)
    }
}
