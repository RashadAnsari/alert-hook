package click.rashad.alert.hook

import io.ktor.locations.*

data class AlertManagerAlert(
    val status: String,
    val labels: HashMap<String, String>,
    val annotations: HashMap<String, String>,
    val startsAt: String,
    val endsAt: String,
    val generatorURL: String
)

@KtorExperimentalLocationsAPI
@Location("/api/v1/alertmanager/{token}")
data class AlertManager(
    val token: String = "",
    val version: String = "",
    val groupKey: String = "",
    val status: String = "",
    val receiver: String = "",
    val groupLabels: HashMap<String, String> = HashMap(),
    val commonLabels: HashMap<String, String> = HashMap(),
    val commonAnnotations: HashMap<String, String> = HashMap(),
    val externalURL: String = "",
    val alerts: ArrayList<AlertManagerAlert> = ArrayList()
) {
    @Location("/balemessenger")
    data class BaleMessenger(val alertManager: AlertManager)
}