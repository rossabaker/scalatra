package org.scalatra

import socketio._


class SocketIOExample extends ScalatraServlet with SocketIOSupport {

  socketio {
    case Connect(client) =>
      println("connecting a client")

    case Message(client, frameType, message) =>
      println("RECV [%s]: %s".format(frameType, message))
      client.send("ECHO: " + message)

    case Disconnect(client, reason, message) =>
      println("Disconnect [%s]: %s".format(reason, message))
  }
}