package click.rashad.alert.hook.alertmanager

import click.rashad.alert.hook.commons.ApiError
import com.typesafe.config.ConfigFactory
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Route

@KtorExperimentalLocationsAPI
fun Route.alertManager() {
    val config = ConfigFactory.load()
    val baleMessengerApiToken =
        config.getString("alertmanager.balemessenger.api-token")

//    val log = LoggerFactory.getLogger(javaClass)

    post<BaleMessenger> {
        if (it.token != baleMessengerApiToken) {
            val status = HttpStatusCode.Forbidden
            call.respond(status, ApiError(status.value, status.description))
        } else {
//            val alertManager = call.receive<AlertManager>()
            call.respond(HttpStatusCode.OK)
        }
    }
}