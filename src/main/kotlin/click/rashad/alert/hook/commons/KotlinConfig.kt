package click.rashad.alert.hook.commons

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

enum class AppType {
    Server,
    Test
}

object KotlinConfig {

    fun load(appType: AppType): Config {
        val configPath = getConfigPath(appType)
        if (Files.exists(Paths.get(configPath))) {
            System.setProperty("config.file", configPath)
        }

        val logbackPath = getLogbackPath()
        if (Files.exists(Paths.get(logbackPath))) {
            System.setProperty("logback.configurationFile", logbackPath)
        }

        return ConfigFactory.load()
    }

    private fun getConfigPath(appType: AppType): String {
        return try {
            val path = File(".").canonicalPath
            when (appType) {
                AppType.Server -> "$path/conf/server.conf"
                AppType.Test -> "$path/conf/test.conf"
            }
        } catch (ex: Exception) {
            ""
        }
    }

    private fun getLogbackPath(): String {
        return try {
            val path = File(".").canonicalPath
            "$path/conf/logback.xml"
        } catch (ex: Exception) {
            ""
        }
    }
}
