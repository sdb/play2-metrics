package com.github.sdb.play2
package metrics

import org.specs2.Specification
import org.specs2.matcher.DataTables
import play.api.Configuration

object ReporterConfigurationSpec extends Specification with DataTables { def is =
  "read period from config"  ! pending^
  "read enabled from config"  ! pending^
  "read unit from config"  ! pending^
  "read host from config"  ! pending^
  "read port from config"  ! pending^
  "read file path from config"  ! pending


//  val data = a.map(a => Map("period" -> a)) getOrElse Map.empty
//  val conf = Configuration.from(data)
//  val reporterConfig = new ReporterConfigurationSupport.ReporterConfiguration(conf)
//  reporterConfig.period must_== b
}
