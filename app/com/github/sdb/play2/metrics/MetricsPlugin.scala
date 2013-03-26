package com.github.sdb.play2
package metrics

import play.api._

class MetricsPlugin(implicit app: Application) extends Plugin {
  import ReporterConfigurationSupport._
  private lazy val logger = Logger("metrics")

  override def onStart() {
    super.onStart()

    val reporterFactories: Seq[(String, (String, Configuration) => Reporter)] = Seq(
      "console" -> (Reporter.console),
      "csv" -> (Reporter.csv),
      "graphite" -> (Reporter.graphite),
      "ganglia" -> (Reporter.ganglia)
    )

    val reportingConfig = app.configuration.getConfig("metrics.reporting") getOrElse Configuration.empty

    reporterFactories foreach { case (name, reporterFactory) =>
      reportingConfig.getConfig(name) collect {
        case config if config.enabled => reporterFactory(name, config)
      } foreach { reporter =>
        logger info("enabling reporter '%s'" format name)
        reporter.enable
      }
    }
  }
}
