abstract class Device {
    protected var screenState: Boolean = false
    
    abstract fun powerOn()
    
    fun powerOff() {
        screenState = false
    }
    
    fun displayScreenStatus() {
        println("The phone screen's light is ${if (screenState) "on" else "off"}.")
    }
}

class FoldablePhone: Device() {
    private var foldingState: Boolean = true
    
    override fun powerOn() {
        screenState = !foldingState
    }
    
    fun foldDevice() {
        foldingState = true
    }
    
    fun unfoldDevice() {
        foldingState = false    
    }
}

fun main() {    
    val newFoldablePhone = FoldablePhone()
    
    newFoldablePhone.powerOn()
    newFoldablePhone.displayScreenStatus()
    newFoldablePhone.unfoldDevice()
    newFoldablePhone.powerOn()
    newFoldablePhone.displayScreenStatus()
}