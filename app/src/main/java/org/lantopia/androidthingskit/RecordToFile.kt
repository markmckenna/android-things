package org.lantopia.androidthingskit

import android.media.MediaRecorder

class RecordToFile(val file: String) : AutoCloseable {
    private val mediaRecorder = MediaRecorder()

    init {
        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file)

            prepare()
            start()
        }
    }

    override fun close() {
        mediaRecorder.apply {
            stop()
            release()
        }
    }
}
