package org.scalatra
package servlet

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import core.Application
import core.http.{Request, Response}

object ScalatraServlet {
  val AppClassKey = "org.scalatra.servlet.AppClass"
}

class ScalatraServlet(private var app: Application) extends HttpServlet 
{
  override def service(servletRequest: HttpServletRequest, 
		       servletResponse: HttpServletResponse) {
    val request = buildRequest(servletRequest)
    app(request) match {
      case Some(response) => render(servletResponse, response)
      case None => noResponse(servletResponse)
    }
  }

  protected def buildRequest(servletRequest: HttpServletRequest): Request =
    new Request {}

  protected def render(servletResponse: HttpServletResponse, 
		       response: Response) {
    servletResponse.setStatus(200)
    servletResponse.getWriter.write("Application responded.")
  }

  protected def noResponse(servletResponse: HttpServletResponse) {
    servletResponse.sendError(HttpServletResponse.SC_NOT_FOUND)
  }
}
