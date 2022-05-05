package net.stckoverflw.apcsnmp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.path.*

object Config {

    lateinit var configuration: Configuration

    private val json = Json {
        prettyPrint = true
    }

    fun load() {
        val path = Path("./scraper.json")
        if (!path.exists() || path.readText().isEmpty()) {
            path.writeText(json.encodeToString(Configuration()))
            error("Please fill the newly created config file scraper.json and restart the application")
        }
        configuration = Json.decodeFromString(path.readText())
    }

}

@Serializable
data class Configuration(
    @SerialName("influx_address") val influxAddress: String = "",
    @SerialName("influx_token") val influxToken: String = "",
    @SerialName("influx_org") val influxOrg: String = "",
    @SerialName("influx_bucket") val influxBucket: String = "",
    val interval: Int = 30,
    val community: String = "",
    val targets: List<TargetConfig> = listOf(TargetConfig()),
    @SerialName("values") val valuesOverride: Map<String, String>? = emptyMap()
)

@Serializable
data class TargetConfig(
    val address: String = "192.168.20.221",
    @SerialName("model_name") val modelName: String = "test",
    @SerialName("community_string") val communityString: String = "wse"
)
