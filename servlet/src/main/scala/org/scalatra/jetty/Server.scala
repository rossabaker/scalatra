package org.scalatra
package jetty

import org.eclipse.jetty.server.{Server => JServer}
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}

import core.Application
import servlet.ScalatraServlet

class Server(app: Application, port: Int) {
  private val server = new JServer(port)

  val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
  context.setContextPath("/")
  context.addServlet(new ServletHolder(new ScalatraServlet(app)), "/*")
  server.setHandler(context)

  def start(): this.type = {
    server.start()
    this
  }

  def stop(): this.type = {
    server.stop()
    this
  }

  def join(): this.type = {
    server.join()
    this
  }
}
