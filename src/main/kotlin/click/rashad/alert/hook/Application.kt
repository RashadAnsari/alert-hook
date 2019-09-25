@file:JvmName("Application")

package click.rashad.alert.hook

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

@KtorExperimentalLocationsAPI
fun main() {
    embeddedServer(
        Netty,
        host = "0.0.0.0",
        port = 8080,
        module = Application::alertHook
    ).apply { start(wait = true) }
}

@KtorExperimentalLocationsAPI
fun Application.alertHook() {
    install(Locations)

    routing {
        alertManager()
    }
}