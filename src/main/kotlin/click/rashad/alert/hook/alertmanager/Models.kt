package click.rashad.alert.hook.alertmanager

data class AlertManagerAlert(
    val status: String = "",
    val labels: HashMap<String, String> = HashMap(),
    val annotations: HashMap<String, String> = HashMap(),
    val startsAt: String = "",
    val endsAt: String = "",
    val generatorURL: String = ""
) {

    fun toTemplateString(template: String): String {
        var result = template
            .replace("{{status}}", status)
            .replace("{{startsAt}}", startsAt)
            .replace("{{endsAt}}", endsAt)
            .replace("{{generatorURL}}", generatorURL)

        labels.map {
            result = result.replace("{{${it.key}}}", it.value)
        }

        annotations.map {
            result = result.replace("{{${it.key}}}", it.value)
        }

        return result.trimIndent()
    }

}

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
) {

    fun toTemplateStringList(template: String): ArrayList<String> {
        return alerts.map { it.toTemplateString(template) }.toCollection(ArrayList())
    }

}