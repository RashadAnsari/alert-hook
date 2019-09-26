package click.rashad.alert.hook.alertmanager

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location

@KtorExperimentalLocationsAPI
@Location("/api/v1/alertmanager/balemessenger/{token}")
data class BaleMessenger(val token: String)