package furhatos.app.anotherskill.flow
import com.amazonaws.util.Classes
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import javax.print.attribute.standard.Destination


val Start : State = state(Interaction) {
    onUserEnter(instant = true) {
        furhat.glance(it)
        onReentry {
            furhat.listen()
        }
    }

    onEntry {
        random(
                {   furhat.say("Hello, and welcome to Roboworld Airlines") },
                {   furhat.say("Hi, welcome to our Airline desk") }
        )
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 1.0))
        furhat.ask("This is a flight booking service. can I help you?")
    }

    onResponse<Yes>{
        furhat.say("That's great. please provide the following details")
        goto(Makebooking)
    }

    onResponse<No>{
        furhat.gesture(Gestures.Surprise(duration = 1.0, strength = 0.5))
        furhat.say("Oh! I am sorry that I am not helpful today")


    }
    //onButton("Book a flight") {
        //furhat.gesture(Gestures.BigSmile)
        //furhat.say("Great.Welcome to book a flight ticket")
       // goto(Makebooking)
   // }
}

// Form-filling state that checks any missing slots and if so, goes to specific slot-filling states.
val Makebooking = state {
    onEntry {
        //val order = users.current.order
        if(users.current.order.departure == null)
            goto(RequestDeparture)
        if(users.current.order.destination == null)
          goto(RequestDestination)
        if(users.current.order.flightclass == null)
            goto(RequestFlightClass)
        if(users.current.order.windowseat == null)
            goto(Requestwindowseat)
        if(users.current.order.addbaggage == null)
          goto(Requestaddbaggage)
            //!order.timeChecked        -> goto(ConfirmTime)

               goto(ConfirmOrder)
            }
        }

// Request destination
val Dest = arrayOf("Paris", "London", "Berlin")

val RequestDestination : State = state {
    onEntry {
        furhat.ask("Where do you want to go?")

    }

    onResponse {
        var destination = it.speech.text
        if (destination in Dest){
            furhat.say("Okay, ${destination}")

            users.current.order.setDest(destination)
        }
            else{
                furhat.say("Incorrect destionation")

        }
        goto(Makebooking)
    }
}

// Request departure
val Dept = arrayOf("Frankfurt", "Stockholm", "Milano")

val RequestDeparture : State = state {
    onEntry {
        furhat.ask("Where do you want to depart from?")

    }

    onResponse {
        var departure = it.speech.text
        if (departure in Dept){
            furhat.say("Okay, ${departure}")

            users.current.order.setDept(departure)
        }
        else{
            furhat.say("Incorrect departure")

        }
        goto(Makebooking)
    }
}

// Request flight classes
val FlightClasses = arrayOf("first class", "economy class", "business class")

val RequestFlightClass : State = state {
    onEntry {
        furhat.ask("Which class do you prefer? First class, Economy class, or Business class?")

    }

    onResponse {
        var Fclass = it.speech.text
        if (Fclass in FlightClasses){
            furhat.say("Okay, ${Fclass}")

            users.current.order.setClass(Fclass)
        }
        else{
            furhat.say("Incorrect class choice")

        }
        goto(Makebooking)
    }
}

val Requestwindowseat : State = state {

    onEntry {

        furhat.ask("Would you prefer a window seat?")

    }

    onResponse<Yes> {
        users.current.order.setWinseat("Yes")
        goto(Makebooking)
    }

    onResponse<No> {
        users.current.order.setWinseat("No")
        goto(Makebooking)
    }

}
// Request baggage
val Requestaddbaggage : State = state {
    onEntry {
        furhat.ask("Do you want to add a checked baggage?")

    }
    onResponse<Yes>{
        goto(RequestbaggageOptions)

    }

    onResponse<No>{
       users.current.order.setBaggage("no baggage")
        goto(ConfirmOrder)

    }

}
// Request baggage
val RequestbaggageOptions : State = state {
    onEntry {
        furhat.ask("We have different options. How many kg do you want to add?")

    }
    onResponse{

       var baggage = it.speech.text
       if(!isInteger(baggage) ){
            furhat.say("Incorrect baggage number")
            goto(Requestaddbaggage)
        }
        else if(baggage.toInt() < 20 || baggage.toInt() > 40){
            furhat.say("Incorrect baggage number")
            goto(Requestaddbaggage)

        }

        furhat.say("Okay, ${baggage}")

        users.current.order.setBaggage(baggage)
        goto(ConfirmOrder)

    }

    onResponse<No>{
        users.current.order.setBaggage("no baggage")


    }

}



// Confirming order
val ConfirmOrder : State = state {
    onEntry {
        furhat.say("Departure " + users.current.order.departure.toString())
        furhat.say("Destination " + users.current.order.destination.toString())
        furhat.say("Class " + users.current.order.flightclass.toString())
        furhat.say("Window seat " + users.current.order.windowseat.toString())
        furhat.say("Baggage " + users.current.order.addbaggage.toString())

        furhat.ask("Is everything correct?")

    }

    onResponse<Yes> {
        furhat.say("Great. You will receive an email with the details of your reservation. " +
                "Thank you for choosing Robo Airlines and have a nice trip")
        goto(Idle)
    }

    onResponse<No> {
        users.current.order.destination = null
        users.current.order.addbaggage = null
        users.current.order.departure = null
        users.current.order.flightclass = null
        users.current.order.windowseat = null

        goto(Makebooking)
    }

}

fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false
