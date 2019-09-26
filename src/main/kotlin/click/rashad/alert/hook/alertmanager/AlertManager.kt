package click.rashad.alert.hook.alertmanager

import click.rashad.alert.hook.commons.ApiError
import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import org.slf4j.LoggerFactory

@KtorExperimentalLocationsAPI
fun Route.alertManager() {
    val config = ConfigFactory.load()

    val baleMessengerApiToken =
        config.getString("alertmanager.balemessenger.api-token")
    val baleMessengerTemplate =
        config.getString("alertmanager.balemessenger.template")

    val log = LoggerFactory.getLogger(javaClass)

    post<BaleMessenger> {
        if (it.token != baleMessengerApiToken) {
            log.warn("Authentication failed for AlertManager and BaleMessenger hook with token: {}", it.token)
            val status = HttpStatusCode.Forbidden
            call.respond(status, ApiError(status.value, status.description))
        } else {
            val body = call.receive<AlertManager>()
            log.info("Request received for AlertManager and BaleMessenger hook with body: \n{}", Gson().toJson(body))
            val alerts = body.toTemplateStringList(baleMessengerTemplate)
            alerts.map { alert ->
                println(alert)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
