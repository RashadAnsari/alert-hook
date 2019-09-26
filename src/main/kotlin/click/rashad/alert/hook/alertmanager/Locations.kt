package click.rashad.alert.hook.alertmanager

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location

data class AlertManagerAlert(
    val status: String = "",
    val labels: HashMap<String, String> = HashMap(),
    val annotations: HashMap<String, String> = HashMap(),
    val startsAt: String = "",
    val endsAt: String = "",
    val generatorURL: String = ""
)

data class AlertManager(
    val version: String = "",
    val groupKey: String = "",
    val status: String = "",
    val receiver: String = "",
    val groupLabels: HashMap<String, String> = HashMap(),
    val commonLabels: HashMap<String, String> = HashMap(),
    val commonAnnotations: HashMap<String, String> = HashMap(),
    val externalURL: String = "",
    val alerts: ArrayList<AlertManagerAlert> = ArrayList()
)

@KtorExperimentalLocationsAPI
@Location("/api/v1/alertmanager/balemessenger/{token}")
data class BaleMessenger(val token: String)