package net.stckoverflw.apcsnmp

import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import java.time.Instant

const val tagName = "model"

fun createPoint(tagValue: String, value: Int, snmpValue: SnmpValue) = Point.measurement(snmpValue.influxName)
    .addTag(tagName, tagValue)
    .addField("value", value)
    .time(Instant.now().toEpochMilli(), WritePrecision.MS)

