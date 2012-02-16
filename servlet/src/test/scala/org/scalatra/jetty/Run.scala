package org.scalatra
package jetty

import core.Application
import core.http.{Request, Response}

object Run extends App {
  val server = new Server(new Application { 
    def apply(req: Request) = Some(new Response{})
  }, 8080).start()
  readLine("Press <enter> to shut down")
  server.stop()
}
