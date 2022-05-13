package furhatos.app.anotherskill.nlu
import furhatos.nlu.*

open class OrderAirlineIntent : Intent(){
    var addbaggage    : String? = null
    var destination  : String? = null
    var departure  : String? = null
    var flightclass  : String? = null
    var windowseat  : String? = null
    fun setDest(dest : String)
    {
        this.destination = dest
    }
    fun setBaggage(bag : String)
    {
        this.addbaggage = bag
    }
    fun setDept(dept : String)
    {
        this.departure = dept
    }
    fun setClass(cls : String)
    {
        this.flightclass = cls
    }
    fun setWinseat(seat : String)
    {
        this.windowseat = seat
    }
}



