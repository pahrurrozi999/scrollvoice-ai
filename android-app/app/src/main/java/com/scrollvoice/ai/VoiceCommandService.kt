package com.scrollvoice.ai

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.app.NotificationCompat

class VoiceCommandService : Service() {

        private var speechRecognizer: SpeechRecognizer? = null
            private var isListening = false

                override fun onCreate() {
                            super.onCreate()
                                    createNotificationChannel()
                                            startForeground(1, createNotification())
                                                    startRecognition()
                }

                    private fun startRecognition() {
                                if (!SpeechRecognizer.isRecognitionAvailable(this)) {
                                                stopSelf()
                                                            return
                                }

                                        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

                                                speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                                                                override fun onReadyForSpeech(params: android.os.Bundle?) {
                                                                                    isListening = true
                    }

                                override fun onBeginningOfSpeech() {}
                                            override fun onRmsChanged(rmsdB: Float) {}
                                                        override fun onBufferReceived(buffer: ByteArray?) {}
                                                                    override fun onEndOfSpeech() {
                                                                                        isListening = false
                    }

                                override fun onError(error: Int) {
                                                    isListening = false
                                                                    if (error != SpeechRecognizer.ERROR_CLIENT) {
                                                                                            restartRecognition()
                    }
                    }

                                override fun onResults(results: android.os.Bundle?) {
                                                    val matches = results?.getStringArrayList(
                                                                            SpeechRecognizer.RESULTS_RECOGNITION
                                                    )

                                                                    val command = matches?.firstOrNull()
                                                                                        ?.trim()
                                                                                                            ?.lowercase()

                                                                                                                            when (command) {
                                                                                                                                                    "scroll" -> sendCommand("SCROLL")
                                                                                                                                                                        "balik" -> sendCommand("BACK")
                                                                                                                                                                                            "stop" -> sendCommand("STOP")
                                                                                                                                                                                                                "lanjut" -> sendCommand("CONTINUE")
                                                                                                                                                                                                                                    "otomatis" -> sendCommand("AUTO")
                                                                                                                                                                                                                                                        "matikan" -> {
                                                                                                                                                                                                                                                                                    sendCommand("OFF")
                                                                                                                                                                                                                                                                                                            stopSelf()
                                                                                                                                                                                                                                                                                                                                    return
                                                                                                                                                                                                                                                        }
                                                                                                                            }

                                                                                                                                            restartRecognition()
                                }

                                            override fun onPartialResults(partialResults: android.os.Bundle?) {}
                                                        override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
                                                })

                                                        restartRecognition()
                    }

                        private fun restartRecognition() {
                                    if (speechRecognizer == null) return

                                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                                            putExtra(
                                                                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                                                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                                            )
                                                                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID")
                                                                                    putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
                                            }

                                                    try {
                                                                    speechRecognizer?.cancel()
                                                                                speechRecognizer?.startListening(intent)
                                                    } catch (_: Exception) {
                                                                    stopSelf()
                                                    }
                        }

                            private fun sendCommand(command: String) {
                                        val intent = Intent("com.scrollvoice.ai.VOICE_COMMAND")
                                                intent.setPackage(packageName)
                                                        intent.putExtra("command", command)
                                                                sendBroadcast(intent)
                            }

                                private fun createNotificationChannel() {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            val channel = NotificationChannel(
                                                                                "scrollvoice_channel",
                                                                                                "ScrollVoice AI",
                                                                                                                NotificationManager.IMPORTANCE_LOW
                                                            )
                                                                        getSystemService(NotificationManager::class.java)
                                                                                        .createNotificationChannel(channel)
                                            }
                                }

                                    private fun createNotification(): Notification {
                                                return NotificationCompat.Builder(this, "scrollvoice_channel")
                                                            .setContentTitle("ScrollVoice AI")
                                                                        .setContentText("Layanan perintah suara aktif")
                                                                                    .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                                                                                                .setOngoing(true)
                                                                                                            .build()
                                    }

                                        override fun onDestroy() {
                                                    speechRecognizer?.destroy()
                                                            speechRecognizer = null
                                                                    super.onDestroy()
                                        }

                                            override fun onBind(intent: Intent?): IBinder? = null
}
                                                    ))
                                }
                }
}