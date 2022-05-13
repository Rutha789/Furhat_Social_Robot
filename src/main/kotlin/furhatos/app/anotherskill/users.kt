package furhatos.app.anotherskill.flow
import furhatos.app.anotherskill.nlu.OrderAirlineIntent
import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User
import furhatos.util.Language
// Associate an order to a user
val User.order by NullSafeUserDataDelegate { OrderAirlineIntent() }

//var GlobalLanguage = Language.ENGLISH_US
var GlobalLanguage = Language.SWEDISH

