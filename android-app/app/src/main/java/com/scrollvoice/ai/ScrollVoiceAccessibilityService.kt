package com.scrollvoice.ai

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

class ScrollVoiceAccessibilityService : AccessibilityService() {

    companion object {
        @Volatile
        var instance: ScrollVoiceAccessibilityService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Tidak membaca isi layar aplikasi lain.
    }

    override fun onInterrupt() {}

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }

    fun scrollDown() {
        swipe(0.78f, 0.28f)
    }

    fun scrollUp() {
        swipe(0.28f, 0.78f)
    }

    private fun swipe(startY: Float, endY: Float) {
        val width = resources.displayMetrics.widthPixels.toFloat()
        val height = resources.displayMetrics.heightPixels.toFloat()

        val path = Path().apply {
            moveTo(width * 0.5f, height * startY)
            lineTo(width * 0.5f, height * endY)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path, 0, 350
                )
            )
            .build()

        dispatchGesture(gesture, null, null)
    }
}
