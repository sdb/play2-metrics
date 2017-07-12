# Metrics plugin for Play Framework 2

The development of this library has been discontinued.

This plugin makes it easier to use [Coda Hale Metrics](http://metrics.codahale.com) in a [Play 2](http://www.playframework.com) application by
providing a mechanism to configure metrics reporting using Play's default configuration support.

## Installation

Add the following dependencies to use Coda Hale Metrics in your project:

    "com.yammer.metrics" % "metrics-core" % "2.2.0"
    "nl.grons" %% "metrics-scala" % "2.2.0"

Add the following dependency to use this plugin:

    libraryDependencies += "com.github.sdb" %% "play2-metrics" % "0.1.0"

Or, if you want to use a snapshot version:

    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

    libraryDependencies += "com.github.sdb" %% "play2-metrics" % "0.2.0-SNAPSHOT"

Register the plugin by adding it to `conf/play.plugins`:

    100:com.github.sdb.play2.metrics.MetricsPlugin

## Configuration

### Defaults

This is the default configuration used by this plugin (see `conf/reference.conf`):

    metrics {
        reporting {
            console {
                enabled = false
                period = 1
                unit = MINUTES
            }

            csv {
                enabled = false
                period = 1
                unit = MINUTES
                output = "."
            }

            graphite {
                enabled = false
                period = 1
                unit = MINUTES
                host = localhost
                port = 2003
            }

            ganglia {
                enabled = false
                period = 1
                unit = MINUTES
                host = localhost
                port = 8649
            }
        }
    }

### Reporting Configuration

#### Reporting to Graphite

Add the following dependency if you want to send metrics to a Graphite instance:

    "com.yammer.metrics" % "metrics-graphite" % "2.2.0"

Example configuration for reporting metrics to Graphite:

    metrics {
        reporting {
            graphite {
                enabled = true
                period = 1
                unit = MINUTES
                host = carbon.hostedgraphite.com
                port = 2003
                prefix = "apikey"
            }
        }
    }

#### Reporting to Console

Example configuration for reporting metrics to the console:

    metrics {
        reporting {
            console {
                enabled = true
                period = 1
                unit = MINUTES
            }
        }
    }

## Configuration on Heroku

When deploying on Heroku you can use the [Hosted Graphite](https://addons.heroku.com/hostedgraphite) add-on for your metrics.
You should add the following system properties to the `Procfile`:

* `metrics.reporting.graphite.enabled`: should be set to `true`
* `metrics.reporting.graphite.host`: should be set to `carbon.hostedgraphite.com`
* `metrics.reporting.graphite.prefix`: should be set to the apikey for Hosted Graphite

An example `Procfile`:

    web: target/start -Dhttp.port=$PORT -Dmetrics.reporting.graphite.enabled=true -Dmetrics.reporting.graphite.host=carbon.hostedgraphite.com -Dmetrics.reporting.graphite.prefix=$HOSTEDGRAPHITE_APIKEY $JAVA_OPTS

## Compatibility

| play2-metrics | Play        | Metrics   |
| ------------- | ----------- | --------- |
| 0.1.0         | 2.1.0       | 2.2.0     |
| 0.2.0         | 2.1.0       | 2.2.0     |

## Licence

This software is licensed under the Apache 2 license, quoted below.

Copyright 2013 Stefan De Boey

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.