package com.thinkminimo.step

import xml.{Text, Node}

class TemplateExample extends Step with UrlSupport with FileUploads {

  object Template {

    def style() = 
      """
      pre { border: 1px solid black; padding: 10px; } 
      body { font-family: Helvetica, sans-serif; } 
      h1 { color: #8b2323 }
      """

    def page(title:String, content:Seq[Node]) = {
      <html>
        <head>
          <title>{ title }</title>
          <style>{ Template.style }</style>
        </head>
        <body>
          <h1>{ title }</h1>
          { content }
          <hr/>
          <a href={url("/date/2009/12/26")}>date example</a>
          <a href={url("/form")}>form example</a>
          <a href={url("/")}>hello world</a>
          <a href={url("/login")}>login</a>
          <a href={url("/logout")}>logout</a>
          <a href={url("/file-upload")}>upload</a>
        </body>
      </html>
    }
  }

  before {
    contentType = "text/html"
  }

  get("/date/:year/:month/:day") {
    Template.page("Step: Date Example", 
    <ul>
      <li>Year: {params(":year")}</li>
      <li>Month: {params(":month")}</li>
      <li>Day: {params(":day")}</li>
    </ul>
    <pre>Route: /date/:year/:month/:day</pre>
    )
  }

  get("/form") {
    Template.page("Step: Form Post Example",
    <form action={url("/post")} method='POST'>
      Post something: <input name='submission' type='text'/>
      <input type='submit'/>
    </form>
    <pre>Route: /form</pre>
    )
  }

  post("/post") {
    Template.page("Step: Form Post Result",
    <p>You posted: {params("submission")}</p>
    <pre>Route: /post</pre>
    )
  }

  get("/login") {
    (session("first"), session("last")) match {
      case (Some(first:String), Some(last:String)) =>
        Template.page("Step: Session Example",
        <pre>You have logged in as: {first + "-" + last}</pre>
        <pre>Route: /login</pre>
        )
      case x:AnyRef =>
        Template.page("Step: Session Example" + x.toString,
        <form action={url("/login")} method='POST'>
        First Name: <input name='first' type='text'/>
        Last Name: <input name='last' type='text'/>
        <input type='submit'/>
        </form>
        <pre>Route: /login</pre>
        )
    }
  }

  post("/login") {
    (params("first"), params("last")) match {
      case (first:String, last:String) => {
        session("first") = first
	session("last") = last
        Template.page("Step: Session Example",
        <pre>You have just logged in as: {first + " " + last}</pre>
        <pre>Route: /login</pre>
        )
      }
    }
  }

  get("/logout") {
    session.invalidate
    Template.page("Step: Session Example",
    <pre>You have logged out</pre>
    <pre>Route: /logout</pre>
    )
  }

  get("/") {
    Template.page("Step: Hello World",
    <h2>Hello world!</h2>
    <p>Referer: { (request referer) map { Text(_) } getOrElse { <i>none</i> }}</p>
    <pre>Route: /</pre>
    )
  }

  get("/file-upload") {
    Template.page("Step: File upload",
      <form action={url("/file-upload")} method='POST' enctype="multipart/form-data">
        File: <input name='file' type='file' />
        <input type='submit'/>
      </form>)
  }

  post("/file-upload") {
    fileItems("file").get
  }

  protected def contextPath = request.getContextPath   
}
