package org.lantopia.androidthingskit

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : Activity() {
    private lateinit var deviceController: DeviceController
    private lateinit var recordToFile: RecordToFile

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler = Handler(Looper.getMainLooper())

        setContentView(R.layout.activity_main)

        closeButton.setOnClickListener { finish() }

//        deviceController = DeviceController(PeripheralManager.getInstance())

        recordToFile = RecordToFile("/sdcard/test.mp4")
    }

    override fun onDestroy() {
        super.onDestroy()

        recordToFile.close()

//        deviceController.close()
    }
}
