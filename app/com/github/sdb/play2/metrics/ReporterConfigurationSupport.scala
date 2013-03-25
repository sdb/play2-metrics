package com.github.sdb.play2
package metrics

import java.io.File
import java.util.concurrent.TimeUnit
import play.api.Configuration

private trait ReporterConfigurationSupport {
  class ReporterConfiguration(val conf: Configuration) {
    def period = conf.getInt("period") getOrElse 1
    def enabled = conf.getBoolean("enabled") getOrElse false
    def unit = conf.getString("unit", Some(Set((TimeUnit.values.toSeq map(_.toString)):_*))) map(TimeUnit.valueOf(_)) getOrElse TimeUnit.MINUTES
    def host = conf.getString("host") getOrElse "localhost"
    def port = conf.getInt("port") getOrElse 2003
    def path(name: String) = new File(conf.getString(name) getOrElse ".")
  }

  implicit def config2reporterConfiguration(c: Configuration) = new ReporterConfiguration(c)
}

private object ReporterConfigurationSupport extends ReporterConfigurationSupport