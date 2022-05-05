# APC UPS SNMP

This Project is a scraper to grab data from a APC UPS Device using SNMP and write them into an InfluxDB database.

My commits are all unsigned since i didn't have my pc and I didn't wanna create a new gpg key for this pc :|

# Why?

In my Internship I got the assignment to get the data from the Device and display them in a Grafana Dashboard, 
and since I couldn't get the apcupsd plugin for Telegraf working I made this to run along with grafana and influx instead of Telegraf.

# Setup (idk why you would wanna do that but you do you)

for a full setup with grafana create a `docker-compose.yml` file in a new directory and paste this into that file:

```yaml
version: "3"

services:
  scraper:
    build: ./apcsnmp/
    volumes:
    - ./scraper.json:/user/app/scraper.json
    depends_on:
      - influxdb
    links:
      - influxdb
  influxdb:
    image: influxdb
    ports:
      - 8086:8086
    expose:
      - 8086
    volumes:
      - ./influx-data/:/var/lib/influxdb2
  grafana:
    image: grafana/grafana:8.4.5
    ports:
      - 3000:3000
    expose:
      - 3000
```

create a new folder called `influx-data`.

clone this repo into the same folder (`git clone git@github.com:StckOverflw/apcsnmp.git`).

and then create a new file called `scraper.json` in the root directory with the following content, we will be filling the values later

```json
{
    "influx_address": "", 
    "influx_token": "",
    "influx_org": "",
    "influx_bucket": "",
    "interval": 30,
    "targets": [
        {
            "address": "192.168.20.221",
            "model_name": "the tag of this target in the influxdb",
            "community_string": ""
        }
    ]
}
```

run `docker-compose up -d` and go to `http://localhost:8086` in your webbrowser, follow the instructions of influxdb and create a new bucket for the data of the targets, now fill the values in the `scraper.json` with these values

restart all services using `docker-compose restart`

I use [this](./grafana-model.json) configuration for the grafana dashboard, it doesn't really support multiple targets tho
