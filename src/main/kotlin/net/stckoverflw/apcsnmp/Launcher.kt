package net.stckoverflw.apcsnmp

import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import dev.inmo.krontab.doInfinity
import kotlinx.coroutines.runBlocking
import org.soulwing.snmp.SimpleSnmpV1Target
import org.soulwing.snmp.SnmpFactory

suspend fun main() {
    Config.load()

    val client = InfluxDBClientKotlinFactory
        .create(
            Config.configuration.influxAddress,
            Config.configuration.influxToken.toCharArray(),
            Config.configuration.influxOrg,
            Config.configuration.influxBucket
        )
    val writeApi = client.getWriteKotlinApi()

    val targets = Config.configuration.targets.map {
        val target = SimpleSnmpV1Target()
        target.address = it.address
        target.community = it.communityString
        target
    }

    val apcUpsValues = ApcUpsValues.values().map {
        if (Config.configuration.valuesOverride?.keys?.contains(it.oid) == true) {
            SnmpValue(it.oid, Config.configuration.valuesOverride!![it.oid]!!)
        } else {
            SnmpValue(it.oid, it.influxName)
        }
    }

    val contexts = targets.map { SnmpFactory.getInstance().newContext(it) }
    runBlocking {
        println("loading values from ${targets.count()} targets")
        doInfinity("/${Config.configuration.interval} * * * *") {
            contexts.forEachIndexed { contextIndex, context ->
                val values = context.get(ApcUpsValues.values().map { it.oid }).get()
                values.forEachIndexed { index, varbind ->
                    writeApi.writePoint(createPoint(Config.configuration.targets[contextIndex].modelName, varbind.asInt(), apcUpsValues[index]))
                }
            }
        }
    }
}