import sbt._

class StepProject(info: ProjectInfo) extends ParentProject(info)
{
  override def shouldCheckOutputDirectories = false

  val jettyGroupId = "org.mortbay.jetty"
  val jettyVersion = "6.1.22"

  lazy val core = project("core", "step", new CoreProject(_)) 
  class CoreProject(info: ProjectInfo) extends DefaultProject(info) {
    val jettytester = jettyGroupId % "jetty-servlet-tester" % jettyVersion % "provided"
    val scalatest = "org.scalatest" % "scalatest" % "1.0" % "provided->default"
    val mockito = "org.mockito" % "mockito-core" % "1.8.2" % "test"
    val fileupload = "commons-fileupload" % "commons-fileupload" % "1.2.1" % "compile"
    val io = "commons-io" % "commons-io" % "1.3.2" % "compile"
  } 

  lazy val example = project("example", "step-example", new ExampleProject(_), core)
  class ExampleProject(info: ProjectInfo) extends DefaultWebProject(info) {
    val jetty6 = jettyGroupId % "jetty" % jettyVersion % "test"
  }
}
