package click.rashad.alert.hook

import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.post
import io.ktor.response.respondText
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
fun Route.alertManager() {
    post<AlertManager.BaleMessenger> {
        println(it)
        call.respondText("Hello, AlertManager!")
    }
}