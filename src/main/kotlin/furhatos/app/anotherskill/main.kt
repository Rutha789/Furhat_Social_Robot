package furhatos.app.anotherskill

import furhatos.app.anotherskill.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class AnotherskillSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
