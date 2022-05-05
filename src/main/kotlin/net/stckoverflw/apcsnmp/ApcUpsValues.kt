package net.stckoverflw.apcsnmp

enum class ApcUpsValues(val oid: String, val influxName: String) {

    UPS_LOAD(".1.3.6.1.4.1.318.1.1.1.4.2.3.0", "load_percent"),

    POWER_USAGE(".1.3.6.1.4.1.318.1.1.1.3.2.1.0", "power_usage"),

    INTERNAL_TEMPERATURE(".1.3.6.1.4.1.318.1.1.1.2.2.2.0", "internal_temp"),

    BATTERY_CAPACITY(".1.3.6.1.4.1.318.1.1.1.2.2.1.0", "battery_capacity"),
    BATTERY_RUNTIME_REMAIN(".1.3.6.1.4.1.318.1.1.1.2.2.3.0", "battery_runtime_remain"),
    BATTERY_SINCE(".1.3.6.1.4.1.318.1.1.1.2.1.2.0", "on_battery"),
    BATTERY_REPLACE(".1.3.6.1.4.1.318.1.1.1.2.2.4.0", "battery_replace"),
    BATTERY_FREQUENCY(".1.3.6.1.4.1.318.1.1.1.3.2.4.0", "battery_frequency"),

    INPUT_VOLTAGE(".1.3.6.1.4.1.318.1.1.1.3.2.1.0", "input_voltage"),

    OUTPUT_VOLTAGE(".1.3.6.1.4.1.318.1.1.1.4.2.1.0", "output_voltage"),
    OUTPUT_FREQUENCY(".1.3.6.1.4.1.318.1.1.1.4.2.2.0", "output_frequency"),
    OUTPUT_LOAD(".1.3.6.1.4.1.318.1.1.1.4.2.3.0", "output_load")

}

data class SnmpValue(
    val oid: String,
    val influxName: String
)
