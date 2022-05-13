package furhatos.app.anotherskill.flow

import furhatos.flow.kotlin.*
import furhatos.util.*

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        furhat.attendNobody()
        //furhat.say("Idle called")
        if (users.count > 1) {
            furhat.attend(users.other)
            goto(Start)
            }
    }

    onUserEnter {
        furhat.attend(it)
        //furhat.say("A new user entered")
        goto(Start)

    }
}


val Interaction: State = state {

    onUserLeave(instant = true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(Start)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)

        /* if (users.count > 0) {
       // if (it == users.current) {
            furhat.attend(users.other)
            //goto(Start)
            furhat.say("I will be back to you when I am done with the current customer")
            //goto(Idle)

            furhat.attend(users.current)
            goto(Start)
        }
        //else{
            //furhat.attend(it)
            //goto(Start)
        //}
        furhat.say("glance")  */
    }

}