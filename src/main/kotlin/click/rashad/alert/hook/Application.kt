@file:JvmName("Application")

package click.rashad.alert.hook

import click.rashad.alert.hook.alertmanager.alertManager
import click.rashad.alert.hook.commons.AppType
import click.rashad.alert.hook.commons.KotlinConfig
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.text.DateFormat

@KtorExperimentalLocationsAPI
fun main() {
    val config = KotlinConfig.load(AppType.Server)

    embeddedServer(
        Netty,
        host = config.getString("endpoints.http.host"),
        port = config.getInt("endpoints.http.port"),
        module = Application::alertHook
    ).apply { start(wait = true) }
}

@KtorExperimentalLocationsAPI
fun Application.alertHook() {
    install(Locations)
    install(CallLogging)
    install(DefaultHeaders)
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    routing {
        alertManager()
    }
}