package org.lantopia.androidthingskit

import android.util.Log
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager

class DeviceController(peripheralManager: PeripheralManager) : SafeCloseable {
    private val leds: List<Gpio>
    private val buttons: List<Gpio>

    init {
        // test code to identify buttons
//        peripheralManager.gpioList.forEach { id ->
//            bindButton(id, peripheralManager, {
//                Log.e("KAYA", "button pressed: " + id)
//            })
//        }

        leds = LedGpio.values()
                .map(LedGpio::hardwareId)
                .map { bindLed(it, peripheralManager) }
                .onEach { blink(it, 100) }

        buttons = ButtonGpio.values()
                .map(ButtonGpio::hardwareId)
                .map { id -> bindButton(id, peripheralManager, { Log.e("KAYA", "button pressed: $id") }) }
    }

    private fun bindButton(pinId: String, peripheralManager: PeripheralManager, action: () -> Unit) =
            peripheralManager.openGpio(pinId).apply {
                setDirection(Gpio.DIRECTION_IN)
                setEdgeTriggerType(Gpio.EDGE_BOTH)
                setActiveType(Gpio.ACTIVE_LOW)
                registerGpioCallback {
                    action.invoke()
                    true
                }
            }

    private fun bindLed(pinId: String, peripheralManager: PeripheralManager) =
            peripheralManager.openGpio(pinId).apply {
                setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
            }

    private fun blink(led: Gpio, durationMs: Long) = led.apply {
        value = true
        Thread.sleep(durationMs)
        value = false
    }

    override fun close() {
//        leds.forEach(Gpio::close)
    }
}

enum class LedGpio(val hardwareId: String) {
    RED("GPIO2_IO02"),
    GREEN("GPIO2_IO00"),
    BLUE("GPIO2_IO05")
}

enum class ButtonGpio(val hardwareId: String) {
    A("GPIO6_IO14"),
    B("GPIO6_IO15"),
    C("GPIO2_IO07")
}
