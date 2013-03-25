package com.github.sdb.play2
package metrics

import java.io.File
import java.util.concurrent.TimeUnit
import com.yammer.metrics.reporting.{CsvReporter, ConsoleReporter}
import play.api.Configuration

private trait Reporter {
  def name: String
  def enable
}

private object Reporter extends ReporterConfigurationSupport {

  case class Console(name: String, period: Long, unit: TimeUnit) extends Reporter {
    def enable = ConsoleReporter.enable(period, unit)
  }

  lazy val console: (String, Configuration) => Reporter = (name, config) =>
    Console(name, config.period, config.unit)


  case class Csv(name: String, period: Long, unit: TimeUnit, outputDir: File) extends Reporter {
    def enable = CsvReporter.enable(outputDir, period, unit)
  }

  lazy val csv: (String, Configuration) => Reporter = (name, config) =>
    Csv(name, config.period, config.unit, config.path("output"))


  trait DynamicReporter extends Reporter {
    def configuration: Configuration
    def className: String
    def call(cls: Class[_])

    def enable = try {
      call(Class.forName(className))
    } catch {
      case e: Exception =>
        throw configuration.reportError("metrics.reporting.%s" format name, "failed to enable reporter %s" format className, Some(e))
    }
  }


  case class Graphite(name: String, configuration: Configuration, period: Long, unit: TimeUnit, host: String, port: Int, prefix: Option[String]) extends DynamicReporter {
    lazy val className = "com.yammer.metrics.reporting.GraphiteReporter"

    def call(cls: Class[_]) = {
      val method = cls.getMethod("enable", classOf[Long], classOf[TimeUnit], classOf[String], classOf[Int], classOf[String])
      method.invoke(null, new java.lang.Long(period), unit, host, new java.lang.Integer(port), prefix.orNull)
    }
  }

  lazy val graphite: (String, Configuration) => Reporter = (name, config) =>
    Graphite(name, config, config.period, config.unit, config.host, config.port, config.getString("prefix"))


  case class Ganglia(name: String, configuration: Configuration, period: Long, unit: TimeUnit, host: String, port: Int) extends DynamicReporter {
    lazy val className = "com.yammer.metrics.reporting.GangliaReporter"

    def call(cls: Class[_]) = {
      val method = cls.getMethod("enable", classOf[Long], classOf[TimeUnit], classOf[String], classOf[Int])
      method.invoke(null, new java.lang.Long(period), unit, host, new java.lang.Integer(port))
    }
  }

  lazy val ganglia: (String, Configuration) => Reporter = (name, config) =>
    Ganglia(name, config, config.period, config.unit, config.host, config.port)
}
