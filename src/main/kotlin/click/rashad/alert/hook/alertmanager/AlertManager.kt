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
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import me.ivmg.telegram.bot
import me.ivmg.telegram.entities.ParseMode
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory

@KtorExperimentalLocationsAPI
fun Route.alertManager() {
    val config = ConfigFactory.load()
    val log = LoggerFactory.getLogger(javaClass)

    val baleMessengerBaseUrl =
        config.getString("alertmanager.balemessenger.base-url")
    val baleMessengerApiToken =
        config.getString("alertmanager.balemessenger.api-token")
    val baleMessengerBotToken =
        config.getString("alertmanager.balemessenger.bot-token")
    val baleMessengerChatId =
        config.getLong("alertmanager.balemessenger.chat-id")
    val baleMessengerTemplate =
        config.getString("alertmanager.balemessenger.template")

    val baleMessengerBot = bot {
        token = baleMessengerBotToken
        apiUrl = "$baleMessengerBaseUrl/bot"
        logLevel = HttpLoggingInterceptor.Level.NONE
    }

    post<BaleMessenger> {
        if (it.token != baleMessengerApiToken) {
            log.warn("Authentication failed for AlertManager and BaleMessenger hook with token: {}", it.token)
            val status = HttpStatusCode.Forbidden
            call.respond(status, ApiError(status.value, status.description))
        } else {
            val requestBody = call.receive<AlertManager>()
            log.info(
                "Request received for AlertManager and BaleMessenger hook with body: \n{}",
                Gson().toJson(requestBody)
            )

            val alerts = requestBody.toTemplateStringList(baleMessengerTemplate)

            alerts.map { alert ->
                baleMessengerBot.sendMessage(baleMessengerChatId, alert)
            }

            call.respond(HttpStatusCode.OK)
        }
    }

    val telegramMessengerBaseUrl =
        config.getString("alertmanager.telegrammessenger.base-url")
    val telegramMessengerApiToken =
        config.getString("alertmanager.telegrammessenger.api-token")
    val telegramMessengerBotToken =
        config.getString("alertmanager.telegrammessenger.bot-token")
    val telegramMessengerChatId =
        config.getLong("alertmanager.telegrammessenger.chat-id")
    val telegramMessengerTemplate =
        config.getString("alertmanager.telegrammessenger.template")

    val telegramMessengerBot = bot {
        token = telegramMessengerBotToken
        apiUrl = "$telegramMessengerBaseUrl/bot"
        logLevel = HttpLoggingInterceptor.Level.NONE
    }

    post<TelegramMessenger> {
        if (it.token != telegramMessengerApiToken) {
            log.warn("Authentication failed for AlertManager and TelegramMessenger hook with token: {}", it.token)
            val status = HttpStatusCode.Forbidden
            call.respond(status, ApiError(status.value, status.description))
        } else {
            val requestBody = call.receive<AlertManager>()
            log.info(
                "Request received for AlertManager and TelegramMessenger hook with body: \n{}",
                Gson().toJson(requestBody)
            )

            val alerts = requestBody.toTemplateStringList(telegramMessengerTemplate)

            alerts.map { alert ->
                telegramMessengerBot.sendMessage(telegramMessengerChatId, alert, ParseMode.MARKDOWN)
            }

            call.respond(HttpStatusCode.OK)
        }
    }

    val matrixBaseUrl =
        config.getString("alertmanager.matrixmessenger.base-url")
    val matrixRoomId =
        config.getString("alertmanager.matrixmessenger.room-id")
    val matrixAccessToken =
        config.getString("alertmanager.matrixmessenger.access-token")
    val matrixApiToken =
        config.getString("alertmanager.matrixmessenger.api-token")
    val matrixTemplate =
        config.getString("alertmanager.matrixmessenger.template")

    post<MatrixMessenger> {
        if (it.token != matrixApiToken) {
            log.warn("Authentication failed for AlertManager and MatrixMessenger hook with token: {}", it.token)
            val status = HttpStatusCode.Forbidden
            call.respond(status, ApiError(status.value, status.description))
        } else {
            val requestBody = call.receive<AlertManager>()
            log.info(
                "Request received for AlertManager and MatrixMessenger hook with body: \n{}",
                Gson().toJson(requestBody)
            )

            val alerts = requestBody.toTemplateStringList(matrixTemplate)

            val url = URL(
                "$matrixBaseUrl/_matrix/client/r0/rooms/$matrixRoomId/send/m.room.message?access_token=$matrixAccessToken"
            )

            alerts.map { alert ->
                val reqBody =
                    Gson().toJson(MatrixSendMessageRequest(body = alert))
                        .toByteArray(Charset.forName("UTF-8"))
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.outputStream.write(reqBody)
                val statusCode = connection.responseCode
                log.info("get response code {} from matrix", statusCode)
            }

            call.respond(HttpStatusCode.OK)
        }
    }
}
