package org.scalatra
package core

import http.{Request, Response}

trait Application extends (Request => Option[Response])
